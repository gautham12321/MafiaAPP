package com.gautham.mafia.ui

import android.annotation.SuppressLint
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
import com.gautham.mafia.Navigation.Loading
import com.gautham.mafia.Navigation.Lobby
import com.gautham.mafia.Navigation.ProfileChange
import com.gautham.mafia.Navigation.RoomFound
import com.gautham.mafia.Navigation.Searching
import com.gautham.mafia.SearchingScreen
import com.mafia2.data.PlayerDet

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
   val setup by viewmodel.setup.collectAsState() // setup for room search and all
   val isSearching by viewmodel._isSearching.collectAsState()
val hostPlayer = setup.hostDetails
  val  userID  by viewmodel._PlayerID.collectAsState() //Might have to opt out of the flow
   BackGroundScreen(ratio = ratioAnimator)
    {
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
                    viewmodel.changeGameSettings(it, true)


                })//formow empty for now


            }
            composable<Lobby>{

                ratio=it.toRoute<Lobby>().ratio
                LobbyScreen(room_id = state.id,
                    onStart = {

                                     viewmodel.goToLoading()

                                       },
                    isHost = it.toRoute<Lobby>().isHost,
                    isConnecting = isConnecting,
                    onChange = {settings->
                        viewmodel.changeGameSettings(settings,sendtoServer = true)
                               },
                    gameSettings = state.gameSettings,
                    players = state.players,
                    hostId=state.host)

            }
            composable<Loading>{

                LoadingScreen()

            }
            composable<JoinRoom> {
                ratio=it.toRoute<JoinRoom>().ratio
                JoinRoom(onSearch = {

                    viewmodel.searchRoom(it)
                    navController.navigate(Searching.apply { roomId=it })

                })
                BackHandler {
                    navController.popBackStack(Home,false)
                }

            }
            composable<Searching>{
                ratio=it.toRoute<Searching>().ratio
                var roomid = it.toRoute<Searching>().roomId
                SearchingScreen(navController,isSearching,setup, onNavigate = {


                    navController.navigate(RoomFound.apply { roomId=roomid })

                })

            }
            composable<RoomFound> {
                val roomId = it.toRoute<Searching>().roomId


                ratio=it.toRoute<RoomFound>().ratio
                RoomFoundScreen(onJoinRoom = {
                    viewmodel.joinRoom(roomId)
                    navController.navigate(Lobby.apply { isHost=false })

                                             },
                    playerdet = PlayerDet(hostPlayer!!.name,
                    hostPlayer.avatar))//Might work to hide button if not host


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



