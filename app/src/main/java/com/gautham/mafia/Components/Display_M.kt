package com.gautham.mafia.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gautham.mafia.Extras.shadow
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.Typography
@Preview(showSystemUi = true, showBackground = false)
@Composable
fun Display_M(modifier: Modifier=Modifier,onDone:(String)->Unit={},text:String="Basil",readonly:Boolean=true) {
    var textinput by remember {

        mutableStateOf(text)

    }
    Card(modifier = modifier
        .shadow(offsetY = 80.dp, borderRadius = 5.dp, blurRadius = 15.dp)
        .size(width = 250.dp, height = 70.dp)
        ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Text(text = query)
            if(!readonly) {

                /*Icon(
                    tint = Black_M,
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                )
                TextField(

                    singleLine = true,
                    textStyle = Typography.labelMedium,
                    value = textinput.uppercase(),

                    onValueChange = {
                        if (it.length < 10){ textinput = it
                        onDone(it)
                        }
                    },
                    modifier = Modifier

                        .weight(1f)
                        ,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardActions = KeyboardActions(onDone = { }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),

                    )*/
                Icon(
                    tint = Black_M,
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                )
                BasicTextField(value = textinput.uppercase(), onValueChange = {
                    if (it.length < 10){ textinput = it
                        onDone(it)
                    }
                },
                    textStyle =  Typography.labelMedium.copy
                        (textAlign = TextAlign.Center),
                    decorationBox = {
                        innerTextField ->

                       innerTextField()


                    })


            }
            else{
                Text(text = text.uppercase(),style = Typography.labelMedium, color = Black_M)

            }




        }

    }
}