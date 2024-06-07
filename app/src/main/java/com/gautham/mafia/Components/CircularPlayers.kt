package com.gautham.mafia.Components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gautham.mafia.R
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.MafiaTheme
import com.gautham.mafia.ui.theme.Red_M2
import com.mafia2.data.PlayerDet
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun CircularPlayers(color:Color= Red_M2, modifier: Modifier, players:List<PlayerDet>, onClick: (Int) -> Unit,content: @Composable  () -> Unit)
{
    val noplayers = players.size
    Box(modifier = modifier, contentAlignment = Alignment.Center){

        Canvas(modifier = Modifier.fillMaxSize()){

            drawCircle(color=color,radius=when{

                noplayers<=6->  370f
                noplayers<=12->410f
                noplayers<=17->400f
                else->410f




                                              }, style =  Stroke(if (noplayers>12) 100f else 150f))



        }
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription ="Role" )
        CircularLayout(players = players, onClick = {})





    }


}


