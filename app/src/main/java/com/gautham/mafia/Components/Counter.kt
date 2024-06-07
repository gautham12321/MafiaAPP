package com.gautham.mafia.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType.Companion.Sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.R
import com.gautham.mafia.ui.theme.Typography

@Preview
@Composable
fun Counter(size: Dp=100.dp, modifier: Modifier=Modifier, currentValue:Int=10, onIncrease:()->Unit={}, onDecrease:()->Unit={}){

    Box(modifier=modifier){
        Row( verticalAlignment = Alignment.CenterVertically,

            modifier = modifier,
            ) {
            Icon(
                modifier = modifier
                    .offset(x = size/1.7f)

                    .clickable {
                        onDecrease()
                    }
                    .size(size),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Decrease",
            )
            Text(
                modifier = modifier.padding(vertical = size/15).fillMaxWidth().weight(1f),
                text = currentValue.toString(),
                style = Typography.labelSmall.copy(fontSize = with(Density(context = LocalContext.current)){
                    size.toSp()
                }/1.6f),
                textAlign = TextAlign.Center
            )
            Icon(
                modifier = modifier.size(size)
                    .offset(x =-( size/1.7f))

                    .clickable {
                        onIncrease()
                    },
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Increase"
            )
        }



    }


}