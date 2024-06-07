package com.gautham.mafia.Components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gautham.mafia.ui.theme.MafiaTheme

@Preview(showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420"
)
@Composable
fun screenPreviews(){
    MafiaTheme() {

        Box (modifier = Modifier.fillMaxSize()){
            BackGroundScreen(ratio = 0f)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Button_M(modifier = Modifier)
                Counter(size = 50.dp, onIncrease = { /*TODO*/ }) {

                }
                Spacer(modifier = Modifier.height(15.dp))
                switch_M(checked = true)
               // Profile(size = 400f, onClick = {}, playerdet = playerdet)
                searchBar_M(modifier = Modifier.padding(15.dp)) {

                }
                Display_M(modifier = Modifier.padding(5.dp))
                Display_M(readonly = false)
            }
        }

    }


}