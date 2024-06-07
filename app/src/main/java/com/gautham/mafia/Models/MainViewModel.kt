    package com.gautham.mafia.Models

    import android.util.Log
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.gautham.mafia.Data.Avatar
    import com.gautham.mafia.Extras.avatarImages
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
    import kotlinx.coroutines.flow.filterNotNull
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
        /*val roomSearch =
            rmc.getRoomSearchStream() .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000L),false)*/
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
        fun searchRoom(roomId:String){
            val roomFound= false
            viewModelScope.launch{
                rmc.findRoom(roomId)

            }

        }

        fun changeGameSettings(it: gameSettings) {
            Log.d("UPDATE",it.toString())
            gameSettings.update {settings->
                it
            }

        }


        override fun onCleared() {
            super.onCleared()
            viewModelScope.launch { rmc.close() }

        }




    }