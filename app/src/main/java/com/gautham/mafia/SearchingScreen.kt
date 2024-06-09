package com.gautham.mafia

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.gautham.mafia.Data.Setup
import com.gautham.mafia.Navigation.JoinRoom
import com.gautham.mafia.Navigation.RoomFound
import com.gautham.mafia.ui.theme.Typography
import com.gautham.mafia.ui.theme.akira


@Composable
fun SearchingScreen(navController: NavHostController,
                    isSearching: Boolean,
                    setup: Setup,
                    onNavigate:()->Unit) {
   val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment  = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

        Text(text = "Searching for room", style = Typography.displayMedium.copy(fontFamily = akira,color = Color.White, textAlign = TextAlign.Center, lineHeight = 50.sp), modifier = Modifier.padding(16.dp))
        CircularProgressIndicator()


    }
    if(isSearching==false)
    {
        LaunchedEffect(key1 = setup.roomFound) {
            if(setup.roomFound==false) {
                Toast.makeText(context, "Room Not Found", Toast.LENGTH_SHORT).show()
            }
        }
        if(setup.roomFound){
            if(RoomFound.toString().substringBefore('@')!=navController.currentBackStackEntry?.destination?.route.toString()){

                     onNavigate()
            }



        }
        else{


            navController.popBackStack(JoinRoom,false)

        }


    }
    }
