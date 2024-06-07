
package com.gautham.mafia.Components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gautham.mafia.globals.RoomID
import com.gautham.mafia.ui.theme.Black_M

import com.gautham.mafia.ui.theme.MafiaTheme
import com.gautham.mafia.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchBar_M(modifier: Modifier=Modifier,onSearch:(String)->Unit){
    var textinput by remember {

        mutableStateOf("")

    }

    Card(modifier = modifier.size(width = 250.dp, height = 70.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
           // Text(text = query)
           TextField(singleLine = true, textStyle = Typography.labelMedium,value = textinput.uppercase(),
                onValueChange = {
                                if(it.length< RoomID.length) textinput=it
                },modifier= Modifier
                   .weight(1f)
                   .fillMaxSize().height(50.dp),
                colors = TextFieldDefaults.colors(
focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )

               , keyboardActions = KeyboardActions(onSearch = {onSearch(textinput)}),
               keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
           )
            IconButton(onClick = { onSearch(textinput) },modifier=Modifier) {
                Icon(tint = Black_M,imageVector = Icons.Default.Search , contentDescription ="Search ",modifier=Modifier.size(100.dp) )


            }

        }

    }


}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun searchPreview(){

    MafiaTheme {
        searchBar_M(onSearch={})
    }



}
