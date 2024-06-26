package com.gautham.mafia.Network

import android.util.Log
import com.gautham.mafia.Components.Action
import com.gautham.mafia.Data.AudioState
import com.gautham.mafia.Data.Setup
import com.mafia2.data.DoAction
import com.mafia2.data.GameState
import com.mafia2.data.Player
import com.mafia2.data.PlayerDet
import com.mafia2.data.Request
import com.mafia2.data.gameSettings
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.ConnectException

interface RealTimeMessagingClient {
    val gameStateChannel: Channel<GameState>
    val audioStateChannel:Channel<AudioState>
    val setupChannel: Channel<Setup>
    fun getGameStateStream(): Flow<GameState>
    fun getAudioStateStream():Flow<AudioState>
    suspend fun loadSession()
    suspend fun sendAction(action: DoAction)
    suspend fun createRoom(host: PlayerDet, gameSettings: gameSettings)
    suspend fun joinRoom(roomId: String, userDet: PlayerDet)
    suspend fun close()
    suspend fun findRoom(roomId: String)
    //fun testMessageStream(): Flow<String>
    //  fun getRoomSearchStream(): Flow<Boolean>


    suspend fun updateGameSetting(roomId: String, gameSettings: gameSettings)
    fun getSetupStream(): Flow<Setup>
    suspend fun sendGameSettings(id: String, value: gameSettings)
    suspend fun syncAllPlayers(roomId: String)
    suspend fun randomizeRoles(roomId: String)
    suspend fun roleRevealed(id: String)
    suspend fun startGame(roomId: String)
    val ExceptionChannel: Channel<Errors>
    fun getErrors(): Flow<Errors>
    suspend fun doaction(
        action: Action,
        roomId: String,

        affectedPlayer: Int,
        player: Player?
    )

    suspend fun resetGame(id: String)
    suspend fun exitRoom(id: String)
}
    class KtorRMC(val client: HttpClient) : RealTimeMessagingClient {
        override val gameStateChannel = Channel<GameState>()
        override val audioStateChannel: Channel<AudioState> = Channel<AudioState>()
        override val setupChannel = Channel<Setup>()
        var session: WebSocketSession? = null
        override val ExceptionChannel = Channel<Errors>()
        override fun getGameStateStream(): Flow<GameState> = gameStateChannel.consumeAsFlow()
        override fun getAudioStateStream(): Flow<AudioState> =audioStateChannel.consumeAsFlow()

        override fun getSetupStream(): Flow<Setup> = setupChannel.consumeAsFlow()
       override fun getErrors() =ExceptionChannel.consumeAsFlow()
        override suspend fun doaction(
            action: Action,
            roomId: String,
            affectedPlayer: Int,
            player: Player?
        ) {
            var text_send = ""
            val doAction:DoAction=DoAction(player!!,affectedPlayer)
            val request=Json.encodeToString(Request(roomId,doAction))
            if(action==Action.VOTE){
                text_send="vote#${request}"


            }
            else{

            text_send="role_action#${request}"

            }
            Log.d("DOACTION", text_send)
            session?.outgoing?.send(Frame.Text(text_send))
        }

        override suspend fun resetGame(id: String) {
            session?.outgoing?.send(Frame.Text("restartGame#${id}"))
        }

        override suspend fun exitRoom(id: String) {
            session?.outgoing?.send(Frame.Text("ExitRoom#${id}"))
        }


        override suspend fun sendGameSettings(id: String, value: gameSettings) {

            val text_send = "game_Settings#${Json.encodeToString(Request(id, value))}"
            session?.outgoing?.send(Frame.Text(text_send))
        }

        override suspend fun syncAllPlayers(roomId: String) {
            val text_send = "Sync_Players#${roomId}"
            session?.outgoing?.send(Frame.Text(text_send))
        }

        override suspend fun randomizeRoles(roomId: String) {
            val text_send = "randomize_roles#${roomId}"
            session?.outgoing?.send(Frame.Text(text_send))
        }

        override suspend fun roleRevealed(id: String) {
            session?.outgoing?.send(Frame.Text("Role_Revealed#${id}"))
        }

        override suspend fun startGame(roomId: String) {
            session?.outgoing?.send(Frame.Text("start_game#${roomId}"))
        }


        //CHECKCKCKCKCKCKKCKCC
        override suspend fun loadSession() {
            Log.d("session", session.toString())
            try {
                session = client.webSocketSession {
                    url("ws://192.168.1.37:8081/play")
                }
                Log.d("session", session.toString())






                ExceptionChannel.send(Errors.ISALLFINE)
                session?.incoming
                    ?.consumeAsFlow()
                    ?.filterIsInstance<Frame.Text>()
                    ?.mapNotNull { frame ->
                        try {
                            Json.decodeFromString<GameState>(frame.readText())

                        } catch (e: Exception) {
                            try {
                                Json.decodeFromString<AudioState>(frame.readText())
                            } catch (e: Exception) {

                                try {
                                    Json.decodeFromString<Setup>(frame.readText())
                                } catch (e: Exception) {

                                    null
                                }
                            }
                        }
                    }
                    ?.collect {
                        when {
                            it is GameState -> gameStateChannel.send(it) //ERROR PRONE
                            it is Setup -> {setupChannel.send(it)
                            Log.d("SENT",it.toString())
                            }
                            it is AudioState -> audioStateChannel.send(it)

                        }

                    }
            } catch (e: ConnectException) {
            Log.e("loadSession", "Network is unreachable", e)
            ExceptionChannel.send(Errors.NETWORKERROR)
        } catch (e: Exception) {
            Log.e("loadSession", "An error occurred", e)
            ExceptionChannel.send(Errors.NETWORKERROR)
        }



        }

        override suspend fun sendAction(action: DoAction) {
            session?.outgoing?.send(Frame.Text("make_turn#${Json.encodeToString(action)}"))
        }

        override suspend fun createRoom(host: PlayerDet, gameSettings: gameSettings) {
            val text_send = "Create_Room#${Json.encodeToString(host)}"

            session?.outgoing?.send(Frame.Text(text_send))

        }

        override suspend fun updateGameSetting(roomId: String, gameSettings: gameSettings) {
            val text_send = "game_Settings#${Json.encodeToString(Request(roomId.uppercase(), gameSettings))}"
            session?.outgoing?.send(Frame.Text(text_send))


        }


        override suspend fun joinRoom(roomId: String, userDet: PlayerDet) {
            val text_send = "Join_Room#${Json.encodeToString(Request(roomId.uppercase(), userDet))}"
            session?.outgoing?.send(Frame.Text(text_send))
        }

        override suspend fun findRoom(roomId: String) {

            val text_send = "Search_Room#${roomId.uppercase()}"
            Log.d("send", text_send)
            session?.outgoing?.send(Frame.Text(text_send))


        }
//tests


        /* override fun getRoomSearchStream(): Flow<Boolean> {
         return flow {
             val existance=session!!.incoming.consumeAsFlow().filterIsInstance<Frame.Text>().mapNotNull {
                 if (it.readText() == "true") {
                     true
                 } else {
                     false
                 }
             }
             emitAll(existance)
         }




     }*/
//CLOSE

        override suspend fun close() {
            session?.close()
            session = null
        }

    }

