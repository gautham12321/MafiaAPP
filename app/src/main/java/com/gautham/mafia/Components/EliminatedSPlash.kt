package com.gautham.mafia.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.R
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.Typography

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF7A0000)
@Composable
fun EliminatedSplashScreen(modifier: Modifier = Modifier) {


    Surface(modifier = Modifier
        .fillMaxSize()
        , color = Black_M) {
        Box(modifier= Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
             Image(painter = painterResource(id = R.drawable.redcross), contentDescription = "",modifier=Modifier.fillMaxHeight().offset(y=40.dp),contentScale = androidx.compose.ui.layout.ContentScale.FillHeight)
            Text(text = "ELIMINATED", style = Typography.displayLarge.copy(color = Color.White, fontSize = 35.sp), modifier = Modifier.padding(16.dp))

        }

    }
}
