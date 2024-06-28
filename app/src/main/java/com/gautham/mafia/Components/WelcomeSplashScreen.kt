package com.gautham.mafia.Components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gautham.mafia.R

@Preview
@Composable
fun MafiaSplashScreen(modifier: Modifier=Modifier.fillMaxSize()) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.welcome))

    LottieAnimation(composition = composition,
        isPlaying = true,modifier = modifier, speed = 1.5f)

}