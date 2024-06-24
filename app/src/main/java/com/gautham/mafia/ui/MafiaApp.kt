package com.gautham.mafia.ui

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.gautham.mafia.Components.BackGroundScreen
import com.gautham.mafia.Components.Button_M
import com.gautham.mafia.Extras.SettingClass
import com.gautham.mafia.Models.MainViewModel
import com.gautham.mafia.Navigation.CreateRoom
import com.gautham.mafia.Navigation.GAMEOVER
import com.gautham.mafia.Navigation.Home
import com.gautham.mafia.Navigation.JoinRoom
import com.gautham.mafia.Navigation.Loading
import com.gautham.mafia.Navigation.Lobby
import com.gautham.mafia.Navigation.MainGame
import com.gautham.mafia.Navigation.ProfileChange
import com.gautham.mafia.Navigation.RoleReveal
import com.gautham.mafia.Navigation.RoomFound
import com.gautham.mafia.Navigation.Searching
import com.gautham.mafia.Network.Errors
import com.gautham.mafia.SearchingScreen
import com.gautham.mafia.ui.theme.Typography
import com.mafia2.data.Phase
import com.mafia2.data.PlayerDet
import kotlinx.coroutines.delay

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
   //val isConnectionError by viewmodel._isConnectionError.collectAsState()
    val errors by viewmodel.errorsFound.collectAsState()
    val noRoles = gameSettings.noMafia+gameSettings.noDoctor+gameSettings.noCitizen+gameSettings.noDetective+gameSettings.noGod
   val setup by viewmodel.setup.collectAsState() // setup for room search and all
   val isSearching by viewmodel._isSearching.collectAsState()
val hostPlayer = setup.hostDetails
  val  userID  by viewmodel._PlayerID.collectAsState() //Might have to opt out of the flow

   BackGroundScreen(ratio = ratioAnimator)
    {
        NavHost(navController = navController, startDestination = Home,enterTransition = {fadeIn()},


            modifier = Modifier) {//Might have to change
            composable<Home> {
                ratio=it.toRoute<Home>().ratio
                Box(modifier = Modifier.padding())
                {
                    HomeScreen(createRoom = {


                        //navController.navigate(CreateRoom)
                        viewmodel.gotoLoc(navController, CreateRoom)
                    }, joinRoom = { navController.navigate(JoinRoom) },
                        profileChange = {

                            navController.navigate(ProfileChange)
                        }, playerdet = playerDetails
                    )

                }


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
                        viewmodel.resetGame()
                        Log.d("TEST","ONSTART CALLED")
                        viewmodel.syncAllPlayers(room_id = state.id,)
                        viewmodel.randomizeRoles(room_id = state.id)

                                       },
                    isHost = it.toRoute<Lobby>().isHost,
                    isConnecting = isConnecting,
                    onChange = {settings->
                        viewmodel.changeGameSettings(settings,sendtoServer = true)
                               },
                    gameSettings = state.gameSettings,
                    players = state.players,
                    hostId=state.host,
                    navState = state.syncNav,
                    forceNav={


                            viewmodel.gotoLoc(navController, Loading, 5000)

                    })

            }
            composable<Loading>{
                ratio=it.toRoute<Loading>().ratio
                LoadingScreen()
                LaunchedEffect(key1 = null) {
                    delay(3000)
                    if(hostPlayer?.id==userID && state.currentPhase==Phase.GAMESTARTING){

                        viewmodel.startGame(room_id = state.id)
                    }
                    viewmodel.gotoLoc(navController, RoleReveal, 5000)


                }

                //Delay for fun



            }
            composable<MainGame>{
                Log.d("TEST","$hostPlayer:$userID")
                if(state.currentPhase==Phase.GAMEOVER){

                    viewmodel.gotoLoc(navController, GAMEOVER)
                }

                ratio=if(state.currentPhase==Phase.NIGHT) 12f else -12f

                MainGamescreen(state = state,userID=userID,viewModel = viewmodel)

            }
            composable<GAMEOVER>{
                ratio=it.toRoute<GAMEOVER>().ratio
                GameOverScreen(navController=navController,state=state, userId = userID,
                 )

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


            BackHandler {
                navController.navigate(JoinRoom)

            }

            }
            composable<RoleReveal> {

                val role = state.RolesMap[userID]!!
                RoleRevealScreen(role = role, onRoleRevealed = {

                    viewmodel.roleRevealed(state.id)

                })

                if(with(state){
                        players.size==roleRevealedNo
                    }){

                viewmodel.gotoLoc(navController,MainGame,delay = 5000)

            }
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
        if(errors==Errors.NETWORKERROR){
            Surface(modifier = Modifier
                .fillMaxSize()
                .alpha(0.8f), color = Color.Black) {
                Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    Text(text = "NETWORK\nERROR",
                        style = Typography.displayLarge.copy(color = Color.White, lineHeight = 40.sp),textAlign = TextAlign.Center)
                    Button_M(text = "Retry", onClick = {viewmodel.loadAgain()})

                }

            }

        }
    }




}





