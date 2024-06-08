package com.gautham.mafia.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.gautham.mafia.Components.BackGroundScreen
import com.gautham.mafia.Extras.SettingClass
import com.gautham.mafia.Models.MainViewModel
import com.gautham.mafia.Navigation.CreateRoom
import com.gautham.mafia.Navigation.Home
import com.gautham.mafia.Navigation.JoinRoom
import com.gautham.mafia.Navigation.Lobby
import com.gautham.mafia.Navigation.ProfileChange
import com.gautham.mafia.Navigation.RoomFound

//Navigation starts here
//Background should be used oustide navhost so that animations of background can be controlled as needed

@Composable
fun MafiaApp(
    navController: NavHostController,
    viewmodel: MainViewModel,
    innerPadding: PaddingValues,

)
{val context = LocalContext.current

    var ratio by remember { mutableStateOf(-7f) } //0f means 0f
    val state by viewmodel.gameState.collectAsState()
    var playerDetails =  viewmodel._userDetails.collectAsState()
    val gameSettings by viewmodel._gameSettings.collectAsState()
   val ratioAnimator by animateFloatAsState(targetValue = ratio, animationSpec = spring(Spring.DampingRatioLowBouncy,Spring.StiffnessLow))
   val isConnecting by viewmodel._isConnecting.collectAsState()
val noRoles = gameSettings.noMafia+gameSettings.noDoctor+gameSettings.noCitizen+gameSettings.noDetective+gameSettings.noGod
    BackGroundScreen(ratio = ratioAnimator) {
        NavHost(navController = navController, startDestination = Home,enterTransition = {fadeIn()},


            modifier = Modifier.padding(innerPadding)) {//Might have to change
            composable<Home> {
                ratio=it.toRoute<Home>().ratio
                HomeScreen(createRoom = {




                    navController.navigate(CreateRoom)
                }, joinRoom = { navController.navigate(JoinRoom) },
                    profileChange = {

                        navController.navigate(ProfileChange)
                    }, playerdet = playerDetails)


            }
            composable<CreateRoom> {
                ratio=it.toRoute<CreateRoom>().ratio
                CreateRoom(gameSettings = gameSettings, onNavigate = {
                    if(noRoles==gameSettings.totalP) {
                        viewmodel.createRoom()

                        navController.navigate(Lobby)
                    }
                    else{

                        Toast.makeText(context, "No of Roles does not match total players", Toast.LENGTH_SHORT).show()

                    }

                }, onChange = {
                    viewmodel.changeGameSettings(it)


                })//formow empty for now


            }
            composable<Lobby>{

                ratio=it.toRoute<Lobby>().ratio
                LobbyScreen(room_id = state.id, onStart = {},
                    isHost = it.toRoute<Lobby>().isHost,
                    isConnecting = isConnecting,
                    onChange = {viewmodel.changeGameSettings(it)},
                    gameSettings = gameSettings,
                    players = state.players,
                    hostId=state.host)

            }
            composable<JoinRoom> {
                ratio=it.toRoute<JoinRoom>().ratio
                JoinRoom()

            }
            composable<RoomFound> {
                ratio=it.toRoute<RoomFound>().ratio
                RoomFoundScreen(onJoinRoom = {navController.navigate(Lobby.apply { isHost=false })}) //Might work to hide button if not host


            }

            composable<ProfileChange> (
                enterTransition = { slideInHorizontally() }


            ){
                ratio=it.toRoute<ProfileChange>().ratio
                ProfileChangeScreen(
                    settings = listOf(SettingClass("Hello"),SettingClass("Hello")),
                    playerDet =playerDetails, onChange = {


                            viewmodel.changeProfile(it)


                    } )//empty
            BackHandler {
                navController.popBackStack(Home,false)
            }// for now

            }



        }
    }




}


