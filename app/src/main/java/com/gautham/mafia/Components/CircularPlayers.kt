package com.gautham.mafia.Components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.gautham.mafia.ui.theme.Green_M
import com.gautham.mafia.ui.theme.Orange_M
import com.gautham.mafia.ui.theme.Red_M2
import com.mafia2.data.GameState
import com.mafia2.data.Player
import com.mafia2.data.Role

@Composable
fun CircularPlayers(
    color: Color = Red_M2,
    modifier: Modifier,
    players: List<Player>,
    onSelectPlayer: (Player) -> Unit,
    state: GameState,

    selectedPlayer: Player?,
    content: @Composable () -> Unit,

)
{val currentRoleTurn=state.currentRoleTurn
    val noplayers = players.size
    Box(modifier = modifier, contentAlignment = Alignment.Center){
val radius=when{

    noplayers<=6->  370f
    noplayers<=12->410f
    noplayers<=17->400f
    else->410f




}
        Canvas(modifier = Modifier.fillMaxSize()){

            drawCircle(color=color,radius=radius, style =  Stroke(if (noplayers>12) 100f else 150f))



        }
        if(currentRoleTurn!=null) {
            Image(
                painter = painterResource(id = currentRoleTurn.Icon),
                contentDescription = "Role", colorFilter = when (currentRoleTurn) {
                    Role.MAFIA->{

                        ColorFilter.tint(Red_M2)
                    }
                    Role.DOCTOR->{
                        ColorFilter.tint(Green_M)

                    }
                    Role.DETECTIVE ->{

                        ColorFilter.tint(Orange_M)

                    }

                    else->{null}
                }
           , modifier = Modifier.size(200.dp) )
        }
        CircularLayout(players = players, onClick = {
            onSelectPlayer(it)

        },selectedPlayer=selectedPlayer,radius=radius)





    }


}


