package com.gautham.mafia.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mafia2.data.PlayerDet


@Composable
fun Profile(
    modifier: Modifier = Modifier,

    size: Float = 570f, onClick: () -> Unit, playerdet: PlayerDet
) {
    val image = playerdet.avatar

    Box(contentAlignment = Alignment.Center,
        modifier = modifier.offset()) {

        Surface(
            modifier = Modifier
                .size(size.dp)
                  ,
            tonalElevation = 1.dp,
            shape = CircleShape,
            color = Color.Black,
            shadowElevation = 20.dp,
        onClick = onClick)
        {
            Image(painter = painterResource(id = image.imageId),
                contentDescription = "profile picture",
                contentScale = ContentScale.Crop,


            )


        }

         }
    
}

