package com.gautham.mafia.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gautham.mafia.Components.Action
import com.gautham.mafia.Components.BackDialog
import com.gautham.mafia.Components.BackGroundScreen
import com.gautham.mafia.Components.CircularPlayers
import com.gautham.mafia.Components.Display_M
import com.gautham.mafia.Components.EliminatedSplashScreen
import com.gautham.mafia.Components.MainActionCard
import com.gautham.mafia.Components.soundButton
import com.gautham.mafia.Extras.GifImage
import com.gautham.mafia.Models.MainViewModel
import com.gautham.mafia.Navigation.Loading
import com.gautham.mafia.R
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.MafiaTheme
import com.gautham.mafia.ui.theme.Red_M
import com.gautham.mafia.ui.theme.Typography
import com.gautham.mafia.ui.theme.akira
import com.mafia2.data.GameState
import com.mafia2.data.Phase
import com.mafia2.data.Player
import com.mafia2.data.Role
import kotlinx.coroutines.delay

@Composable
fun MainGamescreen(
    modifier: Modifier = Modifier,
    state: GameState = GameState(id = "0"),
    userID: Int,
    viewModel: MainViewModel,
    onExit: () -> Unit,
    soundstate: Boolean,
)
{

        var backpress by remember { mutableStateOf(false) }
        val showDetectiveResponse by viewModel._showDetectiveResponse.collectAsState()
        //  val votedList = state.votedPlayersID
        //might default to voting if not voted
        var currentPhase = state.currentPhase
        var player = state.players.find { it.id == userID }
        var playerRole = state.RolesMap[userID]
        var isVoting = state.isVoting
        var showEliminated by remember {
            mutableStateOf(false)
        }
        var actionTime =
            player!!.isAlive && (isVoting || (state.currentPhase == Phase.NIGHT && playerRole == state.currentRoleTurn))
        val colorToPhase by animateColorAsState(if (state.currentPhase == Phase.NIGHT) Red_M else Black_M)
        val offsetText = 60.dp
        var selectedPlayer by remember { mutableStateOf<Player?>(null) }
        val hasVoted = state.votedPlayersID.contains(userID)
        Log.d("VOTE:LIST", hasVoted.toString() + ":" + state.votedPlayersID)
        LaunchedEffect(key1 = player.isAlive) {
            if (player.isAlive == false) {
                showEliminated = true


            }
            delay(5000)
            showEliminated = false
        }
        AnimatedVisibility(
            visible = showEliminated,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Dialog(onDismissRequest = { }, properties = DialogProperties(false, false, false)) {

                EliminatedSplashScreen(modifier = Modifier.fillMaxSize())
            }

        }


        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )
        {
            soundButton(modifier = Modifier.padding(top = 50.dp),state = soundstate,onSoundChange={

                viewModel.changeSound(it)
            }, size = 30.dp)
            Text(
                text = "DAY : ${state.day}",
                style = Typography.displayLarge.copy(color = Color.White, shadow = null),
                modifier = modifier.offset(y = offsetText)
            )
            var textToDisplay = when (state.currentPhase) {
                Phase.GAMESTARTING -> "INTIAL"
                Phase.DAY -> "DAYTIME"

                Phase.NIGHT -> "NIGHT"

                Phase.GAMEOVER -> "GAMEOVER"

            }
            if (state.isVoting) {
                textToDisplay = "VOTING !!"

            }
            Text(
                text = textToDisplay,
                style = Typography.displayMedium.copy(color = Color.White, fontFamily = akira),
                modifier = modifier.offset(y = offsetText)
            )


            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = modifier.weight(1f), contentAlignment = Alignment.Center) {
                CircularPlayers(
                    color = colorToPhase,
                    modifier = Modifier,//.weight(1f),
                    players = state.players,
                    onSelectPlayer = {


                        selectedPlayer = it
                    }, state = state, selectedPlayer = selectedPlayer
                ) {

                }
                androidx.compose.animation.AnimatedVisibility(showDetectiveResponse) {

                    GifImage(
                        modifier = Modifier.fillMaxSize(), data =

                        if (state.isSuspect) R.drawable.thumb_sup else R.drawable.thumbsdown_office
                    )

                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            AnimatedVisibility(
                !actionTime && selectedPlayer != null,
                modifier = Modifier.padding(16.dp)
            )
            {

                Display_M(
                    modifier = Modifier.padding(16.dp),
                    text = if (selectedPlayer == player) "YOU" else selectedPlayer!!.name
                )

            }
            AnimatedVisibility(actionTime) {
                MainActionCard(action = when (currentPhase) {

                    Phase.DAY -> {
                        /*if(isVoting) */Action.VOTE
                    }

                    Phase.NIGHT -> {

                        when (state.currentRoleTurn) {
                            Role.MAFIA -> Action.KILL
                            Role.DETECTIVE -> Action.SUSPECT
                            Role.DOCTOR -> Action.SAVE
                            else -> Action.SUSPECT

                        }

                    }

                    else -> {

                        Action.VOTE
                    }
                },
                    target = selectedPlayer,
                    player = player,
                    modifier = modifier.padding(16.dp),
                    onDeselect = {

                        selectedPlayer = null
                    },
                    onActionDone = { action, target ->


                        viewModel.doAction(action, target)
                        selectedPlayer = null


//cheyyanam

                    },
                    hasVoted = hasVoted
                )
            }
        }
        AnimatedVisibility(visible = backpress) {
            Dialog(
                onDismissRequest = { backpress = false },
                properties = DialogProperties(false, true, true)
            ) {

                BackDialog(onConfirm = onExit)

            }

        }


        BackHandler {

            backpress = true

        }



}



@Preview(showBackground = true, showSystemUi = true,
    device = "spec:width=1080px,height=2340px,dpi=480"
)
@Composable
fun ScreenPreview(ratio:Float= Loading.ratio, content:@Composable ()->Unit={ }){

    MafiaTheme {
        BackGroundScreen(ratio = ratio)
        {
            content()
        }
    }

}