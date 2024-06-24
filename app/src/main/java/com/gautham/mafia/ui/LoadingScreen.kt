package com.gautham.mafia.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gautham.mafia.Components.BackGroundScreen
import com.gautham.mafia.Navigation.Loading
import com.gautham.mafia.Navigation.RoomFound
import com.gautham.mafia.ui.theme.MafiaTheme
import com.gautham.mafia.ui.theme.Typography
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoadingScreen(){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "Loading".uppercase(),style = Typography.displayLarge.copy(color = Color.White) )
        Spacer(modifier = Modifier.height(10.dp))
        CircularProgressIndicator()

    }



}

