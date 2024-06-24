package com.gautham.mafia.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gautham.mafia.ui.theme.Red_M2
import com.mafia2.data.PlayerDet


@Composable
fun Profile(
    modifier: Modifier = Modifier,

    size: Float = 570f,
    onClick: () -> Unit,
    playerdet: PlayerDet,
    color: Color = Color.Black,
    eliminated: Boolean = false
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
            color = if(eliminated) Color.Red else color,
            shadowElevation = 20.dp,
        onClick = {
            if(!eliminated)
            {onClick()}

        }, border = BorderStroke(8.dp, if(eliminated) Color.Red else color)
        )
        {


                Image(
                    painter = painterResource(id = image.imageId),
                    contentDescription = "profile picture",
                    contentScale = ContentScale.Crop,


                    )
                AnimatedVisibility(visible = eliminated) {

                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Eliminated",tint = Red_M2,)

                }



        }

         }
    
}

