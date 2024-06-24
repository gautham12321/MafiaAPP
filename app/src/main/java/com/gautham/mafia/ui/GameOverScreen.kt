package com.gautham.mafia.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gautham.mafia.Components.CircularLayout
import com.gautham.mafia.Navigation.Lobby
import com.gautham.mafia.ui.theme.Green_M
import com.gautham.mafia.ui.theme.Red_M
import com.gautham.mafia.ui.theme.Typography
import com.mafia2.data.GameState
import com.mafia2.data.Role
import kotlinx.coroutines.delay
import kotlin.math.truncate


@Composable
fun GameOverScreen(navController: NavHostController,state:GameState=GameState(id = ""),userId:Int=0) {
//var visible:Boolean=false
    LaunchedEffect(key1 = null) {


       // visible=true
        delay(10000)
      //  onReset()
        navController.popBackStack(Lobby,false)
    }
    val mafiaWon = state.isWinnerMafia
    var villagersPlayers :List<Int> = listOf()
    val mafiaPlayers = state.RolesMap.entries.map {
        if(it.value==Role.MAFIA){
            it.key
        }
        else{

        villagersPlayers += it.key

            null }
    }.filterNotNull()
    Log.d("MAFIA/VILLAGER NO","$mafiaPlayers/$villagersPlayers")
    Log.d("MAPS","$userId/${state.RolesMap}")


    val playerRole = state.RolesMap[userId]!!
    AnimatedVisibility(visible = true,enter = fadeIn(animationSpec = tween(3000,1000)), exit = fadeOut() ) {
        Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Text(text = "GAME OVER",style = Typography.displayLarge.copy(color = Color.White, textAlign = TextAlign.Center, lineHeight = 40.sp)

            )
            CircularLayout(radius =250f, modifier = Modifier.height(500.dp), players = state.players.filter { it.id in (if(mafiaWon) mafiaPlayers else villagersPlayers )}, gameOverScreen = true)
           Text(text = if(mafiaWon) "THE MAFIA WON" else "VILLAGERS WON",style = Typography.titleLarge.copy(
               color = if((state.isWinnerMafia && playerRole==Role.MAFIA) ||!state.isWinnerMafia && playerRole!=Role.MAFIA) Green_M else Red_M, textAlign = TextAlign.Center, lineHeight = 40.sp))



        }
    }

}
