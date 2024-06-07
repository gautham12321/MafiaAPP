package com.gautham.mafia.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gautham.mafia.Components.Button_M
import com.gautham.mafia.Components.Display_M
import com.gautham.mafia.Components.SettingsCard
import com.gautham.mafia.Extras.Copy
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.Grey_M
import com.gautham.mafia.ui.theme.MafiaTheme
import com.mafia2.data.gameSettings
import com.mafia2.data.toList

@Composable
fun LobbyScreen(
    room_id: String,
    modifier: Modifier = Modifier,
    onStart: () -> Unit = {},
    gameSettings: gameSettings = gameSettings(),
    isHost: Boolean = true,
    isConnecting: Boolean,
    onChange: (gameSettings) -> Unit
){
    val gameList = gameSettings.toList()


    Box(modifier=modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {

        var settingsVisible by remember { mutableStateOf(false)}
        Box (modifier = modifier.fillMaxWidth()
            .align(Alignment.BottomCenter)
            .offset(y=-19f.dp),
            contentAlignment = Alignment.Center){
            if(isHost){Button_M(
                text = "START",
                modifier = modifier

                    .fillMaxWidth(fraction = 0.9f)


            )}

        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .offset(y = (50).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Display_M(
                text = room_id, modifier = modifier
                    .padding(vertical = 25.dp)
                    .fillMaxWidth(fraction = 0.9f)
            )
            Row (Modifier.fillMaxWidth(0.9f), horizontalArrangement = Arrangement.Absolute.SpaceAround){

                ActionButton(onClick = { /*TODO*/ }, icon = Icons.Copy())
                ActionButton(onClick = { /*TODO*/ })
                ActionButton(onClick = {settingsVisible=true }, icon = Icons.Default.Settings)


            }






        }

        AnimatedVisibility(visible = settingsVisible,enter = fadeIn(spring(Spring.DampingRatioNoBouncy,Spring.StiffnessLow)),
            exit = fadeOut(spring(Spring.DampingRatioNoBouncy,Spring.StiffnessLow))) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    , color = Black_M.copy(alpha = 0.8f),onClick = {settingsVisible=false}) {}

                    SettingsCard(
                        modifier = modifier, settings = gameSettings,
                        gameList = gameList.toMutableList(), onChange = onChange
                    )

            }



            
        }
        if(isConnecting){
            Box(modifier = Modifier.fillMaxSize().background(Color.White.copy(alpha = 0.3f)),
            ){

                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            }

        }



    }



}

@Composable
fun ActionButton(modifier: Modifier=Modifier,onClick:()->Unit,icon:ImageVector= Icons.Default.Share){
   val buttonSize = 70.dp
    Button(onClick = onClick, shape = RoundedCornerShape(5.dp),
        modifier = modifier.size(buttonSize),
        colors = ButtonDefaults.buttonColors(containerColor = Grey_M),
        contentPadding = PaddingValues(10.dp),
        border = BorderStroke(3.dp, Black_M)) {
        Icon(imageVector = icon, contentDescription = icon.name,modifier=modifier.fillMaxSize())


    }






}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ButtonPrev(){

    MafiaTheme {
        ActionButton(onClick = {})

    }

}