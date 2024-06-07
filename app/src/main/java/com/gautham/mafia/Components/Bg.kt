package com.gautham.mafia.Components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import com.gautham.mafia.ui.theme.Black_M
import com.gautham.mafia.ui.theme.Red_M
import com.gautham.mafia.ui.theme.Red_M2

@Preview
@Composable
fun BackGroundScreen(modifier: Modifier=Modifier,ratio:Float=0f,content: @Composable BoxScope.() -> Unit={}) {
    Surface {

        Canvas(modifier = modifier.fillMaxSize()) {
            drawRect(Red_M, size = Size(width = this.size.width,height = this.size.height), style = Fill)
            drawRect(Black_M, size = Size(width = this.size.width,height = (this.size.height/2f)+(ratio*100)), style = Fill,topLeft = Offset(0f,(this.size.height/2f)-(ratio*100)))

            
        }

       Box(modifier=modifier.fillMaxSize()){
           content()

       }

    }

}

@Preview
@Composable
fun animatedscreenTest(){

var enable by remember {

    mutableStateOf(false)

}
    val slid by animateFloatAsState( if(enable) 10f else -10f, animationSpec = spring(stiffness = Spring.StiffnessLow) )

    Box {

        BackGroundScreen(ratio = slid )


        switch_M(checked = enable, onCheckedChange = {
            enable = it}
            )


        }


}