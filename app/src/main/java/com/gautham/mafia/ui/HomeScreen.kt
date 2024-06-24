package com.gautham.mafia.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gautham.mafia.Components.Button_M
import com.gautham.mafia.Components.Profile

import com.gautham.mafia.R
import com.mafia2.data.PlayerDet

@Composable
fun HomeScreen(
    playerdet: State<PlayerDet>,
    modifier: Modifier=Modifier,
    profileChange:()-> Unit,
    createRoom:()-> Unit,
    joinRoom:()->Unit)
{


        Image(painter = painterResource(id =R.drawable.mafiabg),
            contentDescription =null , contentScale = ContentScale.Fit)



        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Profile(
                modifier =modifier
                    .align(Alignment.End)
                    .offset(x = 170.dp, y = (-20).dp)
                    .weight(2f),
                size = 340f,
                onClick = {profileChange()}, playerdet =playerdet.value
            )
            Column(modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                Button_M(text = "Create Room", modifier = modifier,onClick = {createRoom()})
                Button_M(text = "Join Room", modifier = modifier,onClick = {joinRoom()})
                Spacer(modifier = Modifier.height(50.dp))
            }
        }




}
