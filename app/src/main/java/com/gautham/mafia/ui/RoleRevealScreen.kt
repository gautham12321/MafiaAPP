package com.gautham.mafia.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.Components.Profile
import com.gautham.mafia.Data.Avatar
import com.gautham.mafia.Extras.AutoSizeConstraint
import com.gautham.mafia.Extras.AutoSizeText
import com.gautham.mafia.ui.theme.Typography
import com.mafia2.data.PlayerDet
import com.mafia2.data.Role
@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun RoleRevealScreen(role: Role=Role.MAFIA,onRoleRevealed:()->Unit={}) {
var revealRole by remember { mutableStateOf(false) }


AnimatedVisibility(visible = revealRole,enter = fadeIn(tween(5000,1000)), exit = fadeOut())
{
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        val image = role.Icon
       Profile(
           modifier = Modifier.padding(16.dp), size =350f,
           onClick = {  },
           playerdet = PlayerDet(name = role.name,
           avatar = Avatar(image)),
           color= if(role==Role.MAFIA) Color.Red else Color.White
       )
        Text("YOUR ROLE IS",style = Typography.labelMedium.copy(color = Color.White))
        Text(text=role.name,
            style = Typography.displayLarge.copy(color = if(role==Role.MAFIA)Color.Red else Color.White,
            fontSize = 40.sp),
            modifier = Modifier.padding(16.dp))
    }

    
        }




        AnimatedVisibility(visible = !revealRole,enter = fadeIn(), exit = fadeOut())
        {
            Column(modifier = Modifier
                .clickable {
                    revealRole = true
                    onRoleRevealed()

                }
                .padding(16.dp)
                .alpha(0.5f)
                .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "TAP",
                    modifier = Modifier,
                    style = Typography.displayLarge.copy(
                        color = Color.White,
                        fontSize = 100.sp,
                        lineHeight = 90.sp
                    )
                )
                Text(
                    text = "TO",
                    modifier = Modifier,
                    style = Typography.displayLarge.copy(
                        color = Color.White,
                        fontSize = 50.sp,
                        lineHeight = 90.sp
                    )
                )
                Text(
                    text = "REVEAL",
                    modifier = Modifier,
                    style = Typography.displayLarge.copy(
                        color = Color.White,
                        fontSize = 60.sp,
                        lineHeight = 90.sp
                    )
                )
            }
        }





}
