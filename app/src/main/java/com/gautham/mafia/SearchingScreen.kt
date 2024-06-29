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
import kotlinx.coroutines.delay


@Composable
fun SearchingScreen(navController: NavHostController,
                    isSearching: Boolean,
                    setup: Setup,
                    onNavigate:()->Unit) {
   val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment  = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

        Text(text = "Searching for room", style = Typography.displayMedium.copy(fontFamily = akira,color = Color.White, textAlign = TextAlign.Center, fontSize = 35.sp, lineHeight = 50.sp), modifier = Modifier.padding(16.dp))
        CircularProgressIndicator()


    }
    Log.d("SearchingScreen", "SearchingScreen: $isSearching")
    LaunchedEffect(key1 = isSearching) {
        while(isSearching==true)
        {
            delay(1000)}
            if(!setup.roomFound){
               Log.d ("NAV","${navController.popBackStack(JoinRoom,false)}")
                Toast.makeText(context, "Room Not Found", Toast.LENGTH_SHORT).show()



            }
        else{
           onNavigate()

        }


    }









    }
