package com.gautham.mafia.ui

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gautham.mafia.Components.Display_M
import com.gautham.mafia.Components.FloatingSettings
import com.gautham.mafia.Components.Profile
import com.gautham.mafia.Extras.SettingClass
import com.gautham.mafia.Extras.getNextAvatarImage
import com.gautham.mafia.Extras.getPrevAvatarImage
import com.gautham.mafia.ui.theme.Red_M

import com.mafia2.data.PlayerDet


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileChangeScreen(
    modifier: Modifier = Modifier,
    playerDet: State<PlayerDet>,
    onChange: (PlayerDet) -> Unit,
    settings: State<List<SettingClass>>,
    onSettingChange: (List<SettingClass>) -> Unit,
    onBack: () -> Unit,
    sharedScope: SharedTransitionScope,
    animatedScope: AnimatedContentScope,

) {


    Box (){



        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {


            Box(contentAlignment = Alignment.Center) {
                with(sharedScope) {
                    Profile(
                        size = 400f,
                        onClick = {


                        },
                        playerdet = playerDet.value,
                        modifier = Modifier.sharedElement(
                            sharedScope.rememberSharedContentState(key = "profile"),
                            animatedVisibilityScope = animatedScope
                        )
                    )

                    Row( verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,

                        modifier = modifier.fillMaxWidth().alpha(0.7f),
                    ) {
                        val size = 50.dp
                        val currentImage = playerDet.value.avatar.imageId
                        IconButton(onClick = {   onChange(playerDet.value.copy(avatar = getPrevAvatarImage(currentImage)))
                        },modifier=modifier.padding(16.dp)

                            .size(size),colors = IconButtonDefaults.iconButtonColors(containerColor = Red_M, contentColor = Color.Black)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Increase",
                                modifier = modifier.size(size)

                            )

                        }
                       IconButton(onClick = {   onChange(playerDet.value.copy(avatar = getNextAvatarImage(currentImage)))
                       },modifier=modifier.padding(16.dp)
                           .size(size),colors = IconButtonDefaults.iconButtonColors(containerColor = Red_M, contentColor = Color.Black)) {
                       Icon(
                               imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                               contentDescription = "Increase",
                               modifier = modifier.size(size)

                           )

                       }

                    }
                    }
            }

                Display_M(
                modifier =
                modifier
                    .fillMaxWidth()
                    .offset(y = 20.dp)
                    .padding(30.dp), readonly = false,
                text = playerDet.value.name,
                onDone = {

                    onChange(playerDet.value.copy(name = it))



                })


        }
        FloatingSettings(
            modifier = modifier
                .padding()
                .align(Alignment.TopEnd), items = settings,
            onSettingChange =
        {label,state->
            val settings = settings.value.onEach {
                if(it.label==label){
                    it.state=state
                }
            }
            onSettingChange(settings)
            Log.d("SETTINGS","2.$label $state")



        }
        )
        IconButton(onClick = { onBack() },modifier= Modifier
            .padding(30.dp)
            .align(Alignment.TopStart)) {
            Icon(modifier = modifier.size(40.dp),imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription ="Go Back" )

        }

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
