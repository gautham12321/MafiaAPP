package com.gautham.mafia.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.ui.theme.Red_M
import com.gautham.mafia.ui.theme.Typography

@Preview
@Composable
fun BackDialog(modifier: Modifier=Modifier,textToDisplay:String="EXIT FROM ROOM ?",onConfirm:()->Unit={},onCancel:()->Unit={}) {

    Card(shape = RoundedCornerShape(10.dp), modifier = modifier.width(300.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = textToDisplay, style = Typography.titleMedium.copy(fontSize = 20.sp),
                modifier = Modifier.padding( 10.dp)
            )
            Button(modifier = Modifier.padding(10.dp), shape = RoundedCornerShape(30),
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Red_M),   //might go wrong
            ){
                Text(text = "EXIT".uppercase(), color = Color.White, style = Typography.titleMedium.copy(shadow = null))
            }

        }


    }
}