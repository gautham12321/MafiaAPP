    package com.gautham.mafia.Models

    import android.util.Log
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.gautham.mafia.Data.Setup
    import com.gautham.mafia.Extras.getRandomAvatarImage
    import com.gautham.mafia.Network.RealTimeMessagingClient
    import com.mafia2.data.GameState
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

        var gameState =
            rmc.getGameStateStream()
                .onStart {
                    Log.d("UPDATESTATE","INTIALIZED")
                    isConnecting.value = true }
                .onEach{
                    isConnecting.value = false
                Log.d("UPDATESTATE",it.toString())
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

        var isConnectionError = MutableStateFlow(false)

        var _isConnectionError = isConnectionError.asStateFlow()
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


        override fun onCleared() {
            super.onCleared()
            viewModelScope.launch { rmc.close() }

        }

        fun goToLoading() {
            TODO("Not yet implemented")
        }


    }