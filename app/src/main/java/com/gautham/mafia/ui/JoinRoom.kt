package com.gautham.mafia.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.Components.BackGroundScreen
import com.gautham.mafia.Components.searchBar_M
import com.gautham.mafia.ui.theme.MafiaTheme


@Composable
fun JoinRoom(modifier: Modifier =Modifier,onSearch: (String)->Unit){

    Column(modifier = modifier
        .fillMaxSize()
        .offset(y = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Text(text = "ENTER ROOM ID", style = typography.displayLarge.copy(color = Color.Black,
            textAlign = TextAlign.Center,lineHeight = 40.sp))

        searchBar_M(modifier=Modifier.padding(16.dp), onSearch)

    }



}


