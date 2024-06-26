    package com.gautham.mafia.Models

    import android.content.Context
    import android.media.MediaPlayer
    import android.util.Log
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import androidx.navigation.NavHostController
    import com.gautham.mafia.Audio.SoundCue
    import com.gautham.mafia.Components.Action
    import com.gautham.mafia.Data.AudioState
    import com.gautham.mafia.Data.Setup
    import com.gautham.mafia.Extras.getRandomAvatarImage
    import com.gautham.mafia.Navigation.GAMEOVER
    import com.gautham.mafia.Navigation.NavObject
    import com.gautham.mafia.Network.Errors
    import com.gautham.mafia.Network.RealTimeMessagingClient
    import com.mafia2.data.GameState
    import com.mafia2.data.Player
    import com.mafia2.data.PlayerDet
    import com.mafia2.data.gameSettings
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.delay
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.SharingStarted
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.flow.catch
    import kotlinx.coroutines.flow.onEach
    import kotlinx.coroutines.flow.onStart
    import kotlinx.coroutines.flow.stateIn
    import kotlinx.coroutines.flow.update
    import kotlinx.coroutines.launch
    import java.net.ConnectException
    import javax.inject.Inject

    @HiltViewModel
    class MainViewModel @Inject constructor(private val rmc:RealTimeMessagingClient):ViewModel()
    {
        init {
            viewModelScope.launch {
                rmc.loadSession()


            }

        }


        val userDetails = MutableStateFlow(PlayerDet("GAUTHAM", getRandomAvatarImage()))
        val _userDetails = userDetails.asStateFlow()
        val gameSettings = MutableStateFlow(gameSettings())
        val _gameSettings = gameSettings.asStateFlow()
       var PlayerID = MutableStateFlow(0)
        val _PlayerID = PlayerID.asStateFlow()
        var showDetectiveResponse = MutableStateFlow(false)
        val _showDetectiveResponse = showDetectiveResponse.asStateFlow()
        var setup = rmc.getSetupStream().onEach {setup->Log.d("SETUP",setup.toString())
            if(setup.playerDetails!= null){
                PlayerID.update {
                    setup.playerDetails.id
                }

            }

        }.catch {
            Log.d("SETUPERROR",it.toString())
        }.stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000L), Setup(false)
        )
        var errorsFound = rmc.getErrors().onEach {
            if(it == Errors.NETWORKERROR){
                isConnectionError.value = true
                Log.d("ERROR",it.toString())

            }
            else{
                Log.d("ERROR",it.toString())
                isConnectionError.value = false
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, Errors.ISALLFINE)

        var gameState =
            rmc.getGameStateStream()
                .onStart {
                    Log.d("UPDATESTATE","INTIALIZED")
                    isConnecting.value = true
                        }
                .onEach{
                    isConnecting.value = false
                Log.d("UPDATESTATES",it.toString())

                }
                .catch { e ->

                    isConnectionError.value= e is ConnectException
                }
            .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000L), GameState(id=""))

//TEST CODE STARTS HERE




        //TEST CODE ENDS HERE


        var isConnecting = MutableStateFlow(false)

        var _isConnecting = isConnecting.asStateFlow()

        var isConnectionError = MutableStateFlow(true)
        var showGameOverScreen = MutableStateFlow(false)
        var _showGameOverScreen = showGameOverScreen.asStateFlow()
      //  var _isConnectionError = isConnectionError.asStateFlow()
         var isSearching = MutableStateFlow(false)
            var _isSearching = isSearching.asStateFlow()
        fun changeProfile(playerDet: PlayerDet){

            userDetails.update {
                it.copy(name = playerDet.name,avatar = playerDet.avatar)

            }

        }
        fun createRoom(){
            viewModelScope.launch {
                rmc.createRoom(userDetails.value,gameSettings.value)
                while(isConnecting.value)
                    delay(1000)

                rmc.updateGameSetting(gameState.value.id,gameSettings.value)
            }
        }
        fun joinRoom(roomId:String) {
            viewModelScope.launch {
                rmc.joinRoom(roomId, _userDetails.value)
                while (isConnecting.value)
                    delay(1000)
            }

        }
        fun exitRoom(id: String) {
            viewModelScope.launch {
                rmc.exitRoom(id)
            }

        }
        fun searchRoom(roomId:String){
            val roomFound= false

            viewModelScope.launch{
                isSearching.update { true}

                rmc.findRoom(roomId)
                delay(2000)
                isSearching.update { false}


            }

        }

        fun changeGameSettings(it: gameSettings, sendtoServer: Boolean=false) {
            Log.d("UPDATE",it.toString())
            gameSettings.update {settings->
                it
            }
            if(sendtoServer){
                sendGameSettings()

            }

        }
        fun sendGameSettings(){

            viewModelScope.launch {
                rmc.sendGameSettings(gameState.value.id,gameSettings.value)
            }



        }



        fun gotoLoc(navController: NavHostController, loc: NavObject, delay: Long=0) {
            if(loc.toString().substringBefore('@')!=navController.currentBackStackEntry?.destination?.route.toString()) {

               viewModelScope.launch {
                delay(delay)

                   navController.navigate(loc)

                   if(loc==GAMEOVER){

                       showGameOverScreen.update {
                           true
                       }
                       delay(4000)
                       showGameOverScreen.update {
                           false
                       }

                   }

               }
            }


        }

        fun syncAllPlayers(room_id: String) {
            viewModelScope.launch {
                rmc.syncAllPlayers(room_id)
            }

        }
        fun randomizeRoles(room_id: String)
        {
            viewModelScope.launch {
                rmc.randomizeRoles(room_id)
            }
     }
        override fun onCleared() {
            super.onCleared()
            viewModelScope.launch { rmc.close() }

        }

        fun roleRevealed(id: String) {
           viewModelScope.launch {
               rmc.roleRevealed(id)

           }
        }

        fun startGame(room_id: String) {
            viewModelScope.launch {
                rmc.startGame(room_id)
            }

        }

        fun loadAgain() {
            viewModelScope.launch {
                rmc.loadSession()
            }
        }

        fun doAction(action: Action, target: Int) {
            viewModelScope.launch {

                    rmc.doaction(action = action,roomId=gameState.value.id,player=gameState.value.players.find { it.id==_PlayerID.value },affectedPlayer=target)

                if(action==Action.SUSPECT){
                    showDetectiveResponse.update {
                        true
                    }
                    delay(5000)
                    showDetectiveResponse.update {
                        false
                    }




                }




            }
        }

        fun resetGame() {
            viewModelScope.launch {

                rmc.resetGame(gameState.value.id)
            }
        }

        /*fun setVotedFalse() {
            hasVoted.update {
                false
            }
        }*/
        //AUDIO STARTS HERE
        val soundState = MutableStateFlow(true)
        val _soundState = soundState.asStateFlow()
        var mediaPlayer: MediaPlayer?=null

        var audiostate =
            rmc.getAudioStateStream()
                .onStart {
                    Log.d("AUDIOSTATE","INTIALIZED")

                }
                .onEach{


                }
                .catch { e ->


                }
                .stateIn(viewModelScope,
                    SharingStarted.WhileSubscribed(5000L), AudioState(null)
                )


        fun playAudio(audio: SoundCue, context: Context){
            if(soundState.value) {

                mediaPlayer = MediaPlayer.create(context, audio.sound)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    mediaPlayer?.release()
                }
            }



        }




    }