package com.gautham.mafia.Components

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gautham.mafia.R



import android.util.Log
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperty

@Preview
@Composable
fun soundButton(modifier: Modifier=Modifier, state: Boolean=true, size: Dp =40.dp, onSoundChange: (Boolean) -> Unit={}) {
    var state by remember { mutableStateOf(state) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sound))
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(state) {
        val targetProgress = if (state) 0f else 0.2f
        val startProgress = progress
        val animationSpec = tween<Float>(durationMillis = 500) // Customize duration as needed

        animate(
            initialValue = startProgress,
            targetValue = targetProgress,
            animationSpec = animationSpec
        ) { value, _ ->
            progress = value
        }
    }


    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
            .requiredHeight(size)
            .requiredWidth(size)
            .scale(3f, 3f)
            .clickable {
                state = !state
                Log.d("soundButton", "soundButton: $state:$progress")
                onSoundChange(state)
            },


        )

}

/*
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ButtonPreview(){


        soundButton(Modifier.Companion.align(Alignment.TopStart))

}*/
