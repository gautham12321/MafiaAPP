package com.gautham.mafia.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.Data.Avatar
import com.gautham.mafia.R
import com.gautham.mafia.ui.theme.Green_M
import com.gautham.mafia.ui.theme.Typography
import com.mafia2.data.Player
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LobbyCard(
    it: Player=
        Player(id = 1,
            name = "Gautham",
            avatar = Avatar(imageId = R.drawable._043232_avatar_batman_comics_hero_icon)



        ),
    hostCard:Boolean=false

)
{
    Card(modifier = Modifier
        .padding(10.dp)
        .width(150.dp),
        colors = CardDefaults.cardColors(if(hostCard) Green_M else  Color.White)
        ) {
        Column {
            Image(
                painter = painterResource(id = it.avatar.imageId),
                contentDescription = null,
                contentScale = ContentScale.Fit,modifier = Modifier
            )
            Text(text = it.name.uppercase(), modifier = Modifier
                .padding(15.dp)
                .align(Alignment.CenterHorizontally),
                style = Typography.displaySmall.copy(fontSize = 20.sp))
        }


    }
}