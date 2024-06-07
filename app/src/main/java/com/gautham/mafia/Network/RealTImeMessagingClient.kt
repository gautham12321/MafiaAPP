package com.gautham.mafia.Network

import android.util.Log
import com.mafia2.data.DoAction
import com.mafia2.data.GameState
import com.mafia2.data.PlayerDet
import com.mafia2.data.Request
import com.mafia2.data.gameSettings
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocket
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface RealTimeMessagingClient {
    fun getGameStateStream(): Flow<GameState>
    suspend fun loadSession()
    suspend fun sendAction(action: DoAction)
    suspend fun createRoom(host:PlayerDet,gameSettings: gameSettings)
    suspend fun joinRoom(roomId: String)
    suspend fun  close()
    suspend fun findRoom(roomId: String)
    //fun testMessageStream(): Flow<String>
  //  fun getRoomSearchStream(): Flow<Boolean>


    suspend fun updateGameSetting(roomId: String, gameSettings: gameSettings)
}
class KtorRMC( val client: HttpClient) : RealTimeMessagingClient {

    var session: WebSocketSession?=null

    override fun getGameStateStream(): Flow<GameState> {
        var gameState:Flow<GameState>

        return flow {




            gameState = session!!.incoming
                .consumeAsFlow()
                .onEach { Log.d("messages", "Received message: $it") }
                .catch { Log.e("Error", "Error processing message", it) }
                .filterIsInstance<Frame.Text>()
                .mapNotNull { frame ->
                    try {
                        Json.decodeFromString<GameState>(frame.readText())
                    } catch (e: Exception) {
                        Log.e("Error", "Error decoding JSON", e)
                        null
                    }
                }
            emitAll(gameState)




        }


    }
//CHECKCKCKCKCKCKKCKCC
    override suspend fun loadSession() {
        session =client.webSocketSession{
            url("ws://192.168.1.42:8080/play")
        }
    Log.d("session",session.toString())


    }

    override suspend fun sendAction(action: DoAction) {
        session?.outgoing?.send(Frame.Text("make_turn#${Json.encodeToString(action)}"))
    }

    override suspend fun createRoom(host: PlayerDet, gameSettings: gameSettings)
    {
        val text_send = "Create_Room#${Json.encodeToString(host)}"

        session?.outgoing?.send(Frame.Text(text_send))

    }
    override suspend fun updateGameSetting(roomId: String,gameSettings: gameSettings){
        val text_send = "game_Settings#${Json.encodeToString(Request(roomId,gameSettings))}"
        session?.outgoing?.send(Frame.Text(text_send))



    }



    override suspend fun joinRoom(roomId: String) {
        TODO("Not yet implemented")
    }
    override suspend fun findRoom(roomId: String)
    {
        val text_send = "Search_Room#${roomId}"
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


    override suspend fun close() {
        session?.close()
        session=null
    }

}
