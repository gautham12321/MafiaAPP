package com.gautham.mafia.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gautham.mafia.Components.BackDialog
import com.gautham.mafia.Components.Button_M
import com.gautham.mafia.Components.Display_M
import com.gautham.mafia.Components.LobbyCard
import com.gautham.mafia.Components.SettingsCard
import com.gautham.mafia.Extras.Copy
import com.gautham.mafia.Extras.copyToClipboard
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.Grey_M
import com.gautham.mafia.ui.theme.MafiaTheme
import com.gautham.mafia.ui.theme.Typography
import com.mafia2.data.Player
import com.mafia2.data.gameSettings
import com.mafia2.data.toList

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFF970606)
@Composable
fun LobbyScreen( //LOBBY settings need to be edited only by  the host and when it is edited ,
// it needs to be displayed on the clients phone as well(its not sending gamesettings to server as of now)
    room_id: String = "12345",
    modifier: Modifier = Modifier,
    onStart: () -> Unit = {},
    gameSettings: gameSettings = gameSettings(),
    isHost: Boolean = false,
    isConnecting: Boolean = false,
    onChange: (gameSettings) -> Unit = {},
    players: List<Player> = listOf(),
    hostId: Int? = null,
    navState: Boolean = false,
    forceNav: () -> Unit = {},
    onExit: () -> Unit = {},


){
    var backPressed by remember {
        mutableStateOf(false)
    }

    val gameList = gameSettings.toList()
    val allPlayersJoined =players.size==gameSettings.totalP
    var no_Roles =0
    gameList.forEach {
        no_Roles+=it.no
    }
    val context = LocalContext.current
    if(navState){

        forceNav()
    }
    Box(modifier=modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter)
    {

        var settingsVisible by remember { mutableStateOf(false)}
        Box (modifier = modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .offset(y = -19f.dp),
            contentAlignment = Alignment.Center){
            if(isHost){
                Button_M(size = 25.sp,
                text = if(allPlayersJoined) "START" else "${players.size}/${gameSettings.totalP}",
                modifier = modifier

                    .fillMaxWidth(fraction = 0.9f), enabled = allPlayersJoined,
                onClick = onStart


            )}
            else{
                
                Row(modifier = modifier
                    .padding(15.dp)
                    .fillMaxWidth(fraction = 0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){

                    Text(text = if(allPlayersJoined)"Waiting for host to start".uppercase() else "Waiting for players".uppercase(), style = Typography.titleLarge.copy(fontSize = 17.sp))
                    Spacer(modifier = Modifier.width(40.dp))
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                }
                
            }

        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .offset(y = (20).dp),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top
        ) {


            Display_M(
                text = room_id, modifier = modifier
                    .padding(vertical = 15.dp)
                    .fillMaxWidth(fraction = 0.9f)
            )
            Row (Modifier.fillMaxWidth(0.9f), horizontalArrangement = Arrangement.Absolute.SpaceAround){

                ActionButton(onClick = { copyToClipboard(context,room_id) }, icon = Icons.Copy())
                ActionButton(onClick = { /*TODO*/ })
                ActionButton(onClick = {settingsVisible=true }, icon = Icons.Default.Settings)


            }
            Canvas(modifier = modifier
                .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                .height(3.dp)
                .fillMaxWidth()) {
                drawLine(Black_M, start = Offset(0f, 0f), end = Offset(size.width, 0f), 10f)

            }




                LazyVerticalGrid(modifier = modifier
                    .height(500.dp)
                    .padding(horizontal = 16.dp), columns = GridCells.Adaptive(150.dp)) {
                    items(players) {

                        LobbyCard(it, hostCard = (it.id == hostId) )

                    }


                }









        }

        AnimatedVisibility(visible = settingsVisible,enter = fadeIn(spring(Spring.DampingRatioNoBouncy,Spring.StiffnessLow)),
            exit = fadeOut(spring(Spring.DampingRatioNoBouncy,Spring.StiffnessLow))) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    , color = Black_M.copy(alpha = 0.8f),
                    onClick = {
                        if(no_Roles==gameSettings.totalP){
                            settingsVisible=false
                        }
                        else{
                            Toast.makeText(context, "No of Roles does not match total players", Toast.LENGTH_SHORT).show()

                        }


                        }) {}

                    SettingsCard(
                        modifier = modifier, settings = gameSettings,
                        gameList = gameList.toMutableList(), onChange = onChange,isHost = isHost
                    )

            }



            
        }
        if(isConnecting){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.3f)),
            ){

                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            }

        }
        AnimatedVisibility(visible = backPressed,modifier = Modifier.fillMaxSize(),enter = fadeIn(spring(Spring.DampingRatioNoBouncy,Spring.StiffnessLow)),) {
            Dialog(onDismissRequest = { backPressed=false }, properties = DialogProperties(false,true,true)) {

                BackDialog(onConfirm = {
                    backPressed=false
                    onExit()


                })

            }
            
        }



    }
    BackHandler {
        backPressed=true
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