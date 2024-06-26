package com.gautham.mafia.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gautham.mafia.Components.Display_M
import com.gautham.mafia.Components.FloatingSettings
import com.gautham.mafia.Components.Profile
import com.gautham.mafia.Extras.SettingClass
import com.gautham.mafia.Extras.getRandomAvatarImage

import com.mafia2.data.PlayerDet


@Composable
fun ProfileChangeScreen(
    modifier: Modifier = Modifier,
    playerDet: PlayerDet,
    onChange: (PlayerDet) -> Unit,
    settings: List<SettingClass>
) {


    Box (){



        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {


            Profile(
                size = 400f,
                onClick = {
                onChange(playerDet.copy(avatar = getRandomAvatarImage()))


            },
                playerdet = playerDet, )
            Display_M(
                modifier =
                modifier.fillMaxWidth()
                    .offset(y=20.dp)
                    .padding(30.dp), readonly = false,
                text = playerDet.name,
                onDone = {

                    onChange(playerDet.copy(name = it))



                })


        }
        FloatingSettings(modifier=modifier.align(Alignment.BottomEnd), items = settings)

    }




}



/*
@Preview
@Composable
fun ProfileChangeScreenPreview() {
    ScreenPreview(2f,{ProfileChangeScreen(
        settings = listOf(),
        onChange = {},
        playerDet = PlayerDet(name = "",avatar = getRandomAvatarImage()))})
}*/
