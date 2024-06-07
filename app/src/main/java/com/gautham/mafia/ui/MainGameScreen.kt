package com.gautham.mafia.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gautham.mafia.Components.BackGroundScreen
import com.gautham.mafia.Components.CircularPlayers
import com.gautham.mafia.Components.MainActionCard
import com.gautham.mafia.Navigation.Loading
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.MafiaTheme
import com.gautham.mafia.ui.theme.Red_M2
import com.gautham.mafia.ui.theme.Typography
import com.gautham.mafia.ui.theme.akira
import com.mafia2.data.GameState
import com.mafia2.data.Phase

@Composable
fun MainGamescreen(modifier: Modifier=Modifier,state: GameState=GameState(id="0"))
{var actionTime by remember { mutableStateOf(false) }
    val colorToPhase by animateColorAsState(if(state.currentPhase==Phase.NIGHT) Black_M else Red_M2)
    val offsetText = 60.dp
    Column(modifier=modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround)
    {

        Text(text = "DAY : ${state.day}",
            style = Typography.displayLarge.copy(color = Color.White),
            modifier = modifier.offset(y=offsetText))
        Text(
            text = when(state.currentPhase){
                Phase.GAMESTARTING -> "INTIAL"
                Phase.DAY -> "DAYTIME"
                Phase.NIGHT -> "NIGHT"
                Phase.VOTING -> "VOTE"
                Phase.GAMEOVER -> "GAMEOVER"
            },
            style = Typography.displayMedium.copy(color = Color.White, fontFamily = akira),
            modifier = modifier.offset(y=offsetText)
        )

            CircularPlayers(
                color = colorToPhase,
                modifier = modifier.weight(1f),
                players = listOf(),
                onClick = {

                }) {

            }
        AnimatedVisibility(actionTime) {
            MainActionCard(target = null, modifier = modifier.padding(16.dp))
        }


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