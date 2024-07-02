package com.gautham.mafia.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.Components.SettingsCard
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.Typography
import com.mafia2.data.gameSettings
import com.mafia2.data.toList

@Composable
fun CreateRoom(
    modifier: Modifier = Modifier, gameSettings: gameSettings, onNavigate: () -> Unit,
    onChange: (gameSettings) -> Unit,

)
{//Make viewmodel and use it to change gamesettings as it is a state

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        )

    {
        Text(text = "GAME SETTINGS", textAlign = TextAlign.Center,style = Typography.displayLarge.copy(lineHeight = 30.sp, fontSize = 35.sp),modifier = modifier.offset(y=10.dp).padding(16.dp))

val gameList=gameSettings.toList().toMutableList()
        SettingsCard(modifier, gameSettings, gameList =gameList , onChange = onChange,isHost = true)

        IconButton(onClick = {onNavigate()},modifier = modifier.size(100.dp) ){
            Icon(modifier = modifier.size(500.dp),imageVector = Icons.Default.PlayArrow, contentDescription ="Go To Lobby", tint = Black_M )



        }


        }





}



