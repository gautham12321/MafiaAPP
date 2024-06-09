package com.gautham.mafia.Components

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.ui.theme.Pink_M
import com.gautham.mafia.ui.theme.Red_M2
import com.gautham.mafia.ui.theme.Typography
import com.mafia2.data.Role
import com.mafia2.data.RoleInfo
import com.mafia2.data.gameSettings

@SuppressLint("SuspiciousIndentation")
@Composable
fun SettingsCard(
    modifier: Modifier,
    settings: gameSettings,
    gameList: MutableList<RoleInfo>,
    onChange: (gameSettings) -> Unit,
    isHost:Boolean=false


    ) {
    val context = LocalContext.current



    var totalRoles :Int=0

        gameList.forEach {
            totalRoles += it.no
        }

    Card(
        modifier = modifier
            .height(550.dp)
            .fillMaxWidth(fraction = 0.9f), shape = RoundedCornerShape(20.dp)
    )
    {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            if(!isHost){
                Row(modifier=Modifier.padding(16.dp)){
                    Icon(tint = Red_M2,imageVector = Icons.Filled.Warning, contentDescription = "Only hosts can edit")
                    Text(text = "Only hosts can edit")
                }

            }
            Text(
                text = "Players",
                modifier = modifier.padding(top = 20.dp, bottom = 10.dp),
                style = Typography.titleLarge.copy(fontSize = 40.sp, shadow = null)
            )

            Log.d("UPDATE",settings.toString())
            Counter(currentValue = settings.totalP.toInt(), size = 50.dp,
                onIncrease = {


                   if(isHost) {
                       onChange(settings.copy(totalP = settings.totalP + 1))
                   }

                }, onDecrease = {

                    if(isHost){
                        if (settings.totalP > 4) {
                            onChange(settings.copy(totalP = settings.totalP - 1))

                        } else {
                            Toast.makeText(context, "Minimum 4 players", Toast.LENGTH_SHORT).show()
                        }
                    }
                    })




            LazyVerticalGrid(modifier = modifier.fillMaxSize(), columns = GridCells.Fixed(2)) {
                items(gameList) {
                    val cardDim = 200.dp

                    Card(
                        modifier = Modifier
                            .height(cardDim)
                            .width(cardDim)
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Pink_M)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val roleIndex = gameList.indexOf(it)
                            Image(
                                painter = painterResource(id = it.role.Icon),
                                contentDescription = it.role.name, contentScale = ContentScale.Fit, modifier = Modifier
                                    .padding(10.dp)
                                    .size(100.dp)
                            )
                            Counter(
                                currentValue = it.no,
                                size = 40.dp,
                                onIncrease =
                                {
                                    if(isHost){
                                        if (totalRoles < settings.totalP) {
                                            when (it.role.name) {
                                                Role.MAFIA.name -> {
                                                    onChange(settings.copy(noMafia = settings.noMafia + 1))
                                                }

                                                Role.CITIZEN.name -> {
                                                    onChange(settings.copy(noCitizen = settings.noCitizen + 1))
                                                }

                                                Role.DOCTOR.name -> {
                                                    onChange(settings.copy(noDoctor = settings.noDoctor + 1))
                                                }

                                                Role.DETECTIVE.name -> {
                                                    onChange(settings.copy(noDetective = settings.noDetective + 1))
                                                }


                                            }
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Player Count Exceeded",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }


                                },
                                onDecrease = {
                                  if(isHost)  {
                                        when (it.role.name) {
                                            Role.MAFIA.name -> {
                                                if (settings.noMafia > 0)
                                                    onChange(settings.copy(noMafia = settings.noMafia - 1))
                                            }

                                            Role.CITIZEN.name -> {
                                                if (settings.noCitizen > 0)
                                                    onChange(settings.copy(noCitizen = settings.noCitizen - 1))

                                            }

                                            Role.DOCTOR.name -> {
                                                if (settings.noDoctor > 0)
                                                    onChange(settings.copy(noDoctor = settings.noDoctor - 1))
                                            }

                                            Role.DETECTIVE.name -> {
                                                if (settings.noDetective > 0)
                                                    onChange(settings.copy(noDetective = settings.noDetective - 1))
                                            }

                                        }
                                    }


                                })

                        }


                    }

                }


            }
            //Instead of button maybe use a simple icon?


        }


    }
}
