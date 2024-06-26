package com.gautham.mafia.Components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.Red_M2


@Composable
fun switch_M(label:String="",modifier: Modifier=Modifier, checked:Boolean,
             onCheckedChange:(String,Boolean)->Unit={_,_-> }){
    val l=label
    var chk by remember { mutableStateOf(checked) }

    Switch(checked = chk, onCheckedChange = {
        onCheckedChange(l,it)
        chk=!chk


                                            },
    colors = SwitchDefaults.colors(

        uncheckedBorderColor = Black_M,
        uncheckedTrackColor = Color.White,
        checkedTrackColor = Red_M2, checkedBorderColor = Black_M,

    ), modifier = modifier
    )

}