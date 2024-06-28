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

@Composable
fun soundButton(modifier: Modifier,state: Boolean) {
    var state by remember { mutableStateOf(state) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sound))
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(state) {
        val targetProgress = if (state) 1f else 0f
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
        modifier = modifier.padding(16.dp).clickable {
            state = !state
        }
    )
}



/*
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ButtonPreview(){


        soundButton(Modifier.Companion.align(Alignment.TopStart))

}*/
