package com.gautham.mafia.Components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.Grey_M
import com.gautham.mafia.ui.theme.MafiaTheme
import com.gautham.mafia.ui.theme.Red_M
import com.gautham.mafia.ui.theme.Typography
import com.mafia2.data.Player
enum class Action{
    VOTE,
    KILL,
    SAVE,
    SUSPECT


}
@Composable
fun MainActionCard(modifier: Modifier=Modifier,target:Player?,action:Action=Action.VOTE){

val textToDisplay=when(action){
    Action.VOTE -> "VOTE SOMEONE OUT"
    Action.KILL -> "SHH WHO YA KILLIN?"
    Action.SAVE -> "WHO ARE YOU SAVING?"
    Action.SUSPECT -> "WHO DO YA SUSPECT"
}
    var buttonText :String=""
    var buttonColor : Color= Red_M
    if(target!=null ){

        buttonText="CONFIRM"
        buttonColor= Color(0xFF14AC28)

    }
    else{
        if(action==Action.VOTE){
            buttonText= "Skip vote"
            buttonColor= Color.Red
        }
        else{
            buttonText= "Confirm"
            buttonColor= Color.Gray
        }
    }
    Card(shape = RoundedCornerShape(10.dp), modifier = modifier.width(300.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceEvenly) {
            Text(text = textToDisplay, style = Typography.titleMedium,
                modifier = Modifier.padding(top=20.dp))

            if(target!=null) {
                Spacer(modifier = Modifier.height(40.dp))
                Surface(modifier.fillMaxWidth(), color = Color.LightGray) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Text(
                            text = target.name.uppercase(),
                            style = Typography.titleLarge.copy(fontSize = 35.sp),
                            modifier = Modifier.padding(10.dp)
                        )
                        IconButton(
                            onClick = { /*TODO*/ },
                            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.LightGray),
                            modifier = Modifier.size(25.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "close"
                            )

                        }


                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier.padding(10.dp), shape = RoundedCornerShape(30),
    onClick = {},
                colors =ButtonDefaults.buttonColors(containerColor = buttonColor)
            ){
                Text(text = buttonText.uppercase(), color = Color.White, style = Typography.titleMedium.copy(shadow = null))
            }


        }

    }




}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainActionCardPreview(){
    MafiaTheme {
        MainActionCard(target = null, action = Action.KILL)
    }
}