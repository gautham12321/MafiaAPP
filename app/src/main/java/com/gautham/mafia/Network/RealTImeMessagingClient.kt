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
    val audioStateChannel: Channel<AudioState>
    val setupChannel: Channel<Setup>
    fun getGameStateStream(): Flow<GameState>
    fun getAudioStateStream(): Flow<AudioState>
    suspend fun loadSession()
    suspend fun sendAction(action: DoAction)
    suspend fun createRoom(host: PlayerDet, gameSettings: gameSettings)
    suspend fun joinRoom(roomId: String, userDet: PlayerDet)
    suspend fun close()
    suspend fun findRoom(roomId: String)
    // fun testMessageStream(): Flow<String>
    // fun getRoomSearchStream(): Flow<Boolean>
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
    override fun getAudioStateStream(): Flow<AudioState> = audioStateChannel.consumeAsFlow()
    override fun getSetupStream(): Flow<Setup> = setupChannel.consumeAsFlow()
    override fun getErrors() = ExceptionChannel.consumeAsFlow()

    private fun logMessage(tag: String, message: String) {
        Log.d(tag, message)
    }

    override suspend fun doaction(
        action: Action,
        roomId: String,
        affectedPlayer: Int,
        player: Player?
    ) {
        val textSend: String
        val doAction = DoAction(player!!, affectedPlayer)
        val request = Json.encodeToString(Request(roomId, doAction))
        textSend = if (action == Action.VOTE) {
            "vote#$request"
        } else {
            "role_action#$request"
        }
        logMessage("DOACTION", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun resetGame(id: String) {
        val textSend = "restartGame#$id"
        logMessage("RESET_GAME", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun exitRoom(id: String) {
        val textSend = "ExitRoom#$id"
        logMessage("EXIT_ROOM", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun sendGameSettings(id: String, value: gameSettings) {
        val textSend = "game_Settings#${Json.encodeToString(Request(id, value))}"
        logMessage("SEND_GAME_SETTINGS", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun syncAllPlayers(roomId: String) {
        val textSend = "Sync_Players#$roomId"
        logMessage("SYNC_ALL_PLAYERS", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun randomizeRoles(roomId: String) {
        val textSend = "randomize_roles#$roomId"
        logMessage("RANDOMIZE_ROLES", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun roleRevealed(id: String) {
        val textSend = "Role_Revealed#$id"
        logMessage("ROLE_REVEALED", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun startGame(roomId: String) {
        val textSend = "start_game#$roomId"
        logMessage("START_GAME", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun loadSession() {
        logMessage("LOAD_SESSION", session.toString())
        try {
            session = client.webSocketSession {
                url("ws://34.47.132.185/play")
            }
            logMessage("LOAD_SESSION", session.toString())

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
                    when (it) {
                        is GameState ->{ gameStateChannel.send(it)
                            logMessage("Recieved_State", it.toString())

                        }
                        is Setup -> {
                            setupChannel.send(it)
                            logMessage("Recieved_Setup", it.toString())
                        }
                        is AudioState -> {
                            audioStateChannel.send(it)
                            logMessage("Recieved_AudioState", it.toString())
                        }
                        }
                }
        } catch (e: ConnectException) {
            logMessage("LOAD_SESSION", "Network is unreachable: ${e.message}")
            ExceptionChannel.send(Errors.NETWORKERROR)
        } catch (e: Exception) {
            logMessage("LOAD_SESSION", "An error occurred: ${e.message}")
            ExceptionChannel.send(Errors.NETWORKERROR)
        }
    }

    override suspend fun sendAction(action: DoAction) {
        val textSend = "make_turn#${Json.encodeToString(action)}"
        logMessage("SEND_ACTION", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun createRoom(host: PlayerDet, gameSettings: gameSettings) {
        val textSend = "Create_Room#${Json.encodeToString(host)}"
        logMessage("CREATE_ROOM", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun updateGameSetting(roomId: String, gameSettings: gameSettings) {
        val textSend = "game_Settings#${Json.encodeToString(Request(roomId.uppercase(), gameSettings))}"
        logMessage("UPDATE_GAME_SETTING", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun joinRoom(roomId: String, userDet: PlayerDet) {
        val textSend = "Join_Room#${Json.encodeToString(Request(roomId.uppercase(), userDet))}"
        logMessage("JOIN_ROOM", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun findRoom(roomId: String)
    {
        val textSend = "Search_Room#${roomId.uppercase()}"
        logMessage("FIND_ROOM", textSend)
        session?.outgoing?.send(Frame.Text(textSend))
    }

    override suspend fun close() {
        session?.close()
        session = null
    }
}
