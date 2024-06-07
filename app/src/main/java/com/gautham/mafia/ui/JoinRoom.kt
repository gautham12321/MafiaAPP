package com.gautham.mafia.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gautham.mafia.Components.BackGroundScreen
import com.gautham.mafia.Components.searchBar_M
import com.gautham.mafia.ui.theme.MafiaTheme


@Composable
fun JoinRoom(modifier: Modifier =Modifier){
    Column(modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Text(text = "ENTER ROOM ID", style = typography.titleLarge.copy(color = Color.Black))

        searchBar_M(modifier=Modifier.padding(16.dp)) {searchterm->

        }

    }


}


