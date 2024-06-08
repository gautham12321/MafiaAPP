package com.gautham.mafia.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.ui.theme.MafiaTheme
import com.gautham.mafia.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun Button_M(text:String="Create Room",
             modifier: Modifier=Modifier, size:TextUnit=35.sp,
             onClick: ()->Unit={}, colour:ButtonColors= ButtonDefaults.buttonColors(disabledContainerColor = Color.Gray), enabled:Boolean=true){

  Button(onClick = onClick,
        modifier = modifier.padding(vertical = 15.dp)
        ,shape=shapes.medium,
        contentPadding =
        PaddingValues(horizontal = 6.dp, vertical = 20.dp),
        colors = colour, enabled = enabled




    ) {
        Text(text = text.uppercase(),
            style = Typography.titleLarge,
            fontSize = size,

        )


    }

}

