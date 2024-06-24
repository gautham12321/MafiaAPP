package com.gautham.mafia.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.Components.Button_M
import com.gautham.mafia.Components.Profile
import com.gautham.mafia.ui.theme.Grey_M
import com.gautham.mafia.ui.theme.Typography
import com.mafia2.data.PlayerDet

@Composable
fun RoomFoundScreen(modifier: Modifier =Modifier,
                    onJoinRoom:()->Unit={},
                    playerdet: PlayerDet=PlayerDet("")){
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround) {
        Spacer(modifier = Modifier.height(30.dp))
Surface(modifier = modifier.fillMaxWidth(), color = Grey_M) {


    Text(text ="ROOM FOUND",
        style = Typography.displayLarge.copy(lineHeight = 39.sp),
        textAlign = TextAlign.Center, modifier = modifier.padding(5.dp))

}
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Profile(size = 250f, onClick = {}, playerdet = playerdet)
            Text(
                modifier = Modifier, text = "${playerdet.name}'s Room".uppercase(),
                style = Typography.titleLarge.copy(
                    fontSize = 29.sp,
                    color = Color.Black,
                    shadow = null
                )
            )
        }
Button_M(text = "Join",modifier = modifier.width(200.dp), onClick = {

    onJoinRoom()

})




    }



}

