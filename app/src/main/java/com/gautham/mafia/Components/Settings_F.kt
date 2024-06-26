package com.gautham.mafia.Components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gautham.mafia.Extras.SettingClass
import com.gautham.mafia.ui.theme.Typography

@Composable
 fun FloatingSettings(
    modifier: Modifier=Modifier,
    fabicon:ImageVector=Icons.Default.Settings,
    items: State<List<SettingClass>>,
    onSettingChange:(String, Boolean)->Unit
) {
     //get the reveal done
        var expanded by remember { mutableStateOf(false) }
        var viewSettings by remember { mutableStateOf(false) }
    var fabsize = 100.dp
    var iconSize = 60.dp
    val expandedH
    by animateDpAsState(targetValue = if(!expanded) fabsize else 500.dp,animationSpec = spring(dampingRatio = 2f),
        )
    val expandedW by animateDpAsState(targetValue = if(!expanded) fabsize else 350.dp,animationSpec = spring(dampingRatio = 2f))
    val expandedOpacity by animateFloatAsState(targetValue = if(!expanded) 1f else 0.06f,animationSpec = spring(dampingRatio = 3f))
    val expandedIconSize by animateDpAsState(targetValue = if(!expanded) iconSize else 300.dp,animationSpec = spring(dampingRatio = 1f))
    val expandedRotation by animateFloatAsState(targetValue = if(!expanded) 0f else 100f,animationSpec = spring(dampingRatio = 3f,stiffness = 100f))
    FloatingActionButton(
            onClick = { expanded = !expanded
                        viewSettings = expanded
                      }, shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .padding(30.dp)
                .width(expandedW)
                .height(expandedH)
        ) {

            Box (modifier = Modifier.fillMaxSize()){

                Icon(
                    imageVector = fabicon,
                    contentDescription = "settings",
                    modifier = Modifier
                        .size(expandedIconSize)
                        .align(Alignment.Center)
                        .rotate(expandedRotation),
                    tint = LocalContentColor.current.copy(alpha = expandedOpacity)
                )
                AnimatedVisibility(visible = viewSettings,enter = fadeIn(tween(500)),exit = fadeOut(tween(500))) {
                    LazyColumn(modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxSize()
                        .padding(20.dp)) {
                        items(items=items.value){
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = it.label,style = Typography.labelMedium.copy(fontSize = 15.sp))
                                switch_M(label = it.label,checked = it.state,onCheckedChange =
                                {
                                   label,state->

                                    onSettingChange(label,state)
                                    Log.d("SETTINGS","1.$label $state")


                                })

                            }

                        }
                    }
                }



            }



        }

}

/*@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FloatingSettingsPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        MafiaTheme {
            FloatingSettings(
                modifier = Modifier.align(Alignment.BottomEnd),
                items = listOf(SettingClass("Daddy Undo", ramdomEnabled), SettingClass(
                    "Gay",
                    ramdomEnabled
                ))
            )
        }
    }

    }*/

//Test
@Composable
fun CustomFloatingActionButton(
    expandable: Boolean,
    onFabClick: () -> Unit,
    fabIcon: ImageVector
) {
    var isExpanded by remember { mutableStateOf(false) }
    if (!expandable) { // Close the expanded fab if you change to non expandable nav destination
        isExpanded = false
    }

    val fabSize = 64.dp
    val expandedFabWidth by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else fabSize,
        animationSpec = spring(dampingRatio = 3f)
    )
    val expandedFabHeight by animateDpAsState(
        targetValue = if (isExpanded) 58.dp else fabSize,
        animationSpec = spring(dampingRatio = 3f)
    )

    Column {

        // ExpandedBox over the FAB
        Box(
            modifier = Modifier
                .offset(y = (25).dp)
                .size(
                    width = expandedFabWidth,
                    height = (animateDpAsState(
                        if (isExpanded) 225.dp else 0.dp,
                        animationSpec = spring(dampingRatio = 4f)
                    ))
                        .value
                ),


        ) {
            // Customize the content of the expanded box as needed
        }

        FloatingActionButton(
            onClick = {
                onFabClick()
                if (expandable) {
                    isExpanded = !isExpanded
                }
            },
            modifier = Modifier
                .width(expandedFabWidth)
                .height(expandedFabHeight),
            shape = RoundedCornerShape(18.dp)

        ) {

            Icon(
                imageVector = fabIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .offset(
                        x = animateDpAsState(
                            if (isExpanded) -70.dp else 0.dp,
                            animationSpec = spring(dampingRatio = 3f)
                        ).value
                    )
            )

            Text(
                text = "Create Reminder",
                softWrap = false,
                modifier = Modifier
                    .offset(
                        x = animateDpAsState(
                            if (isExpanded) 10.dp else 50.dp,
                            animationSpec = spring(dampingRatio = 3f)
                        ).value
                    )
                    .alpha(
                        animateFloatAsState(
                            targetValue = if (isExpanded) 1f else 0f,
                            animationSpec = tween(
                                durationMillis = if (isExpanded) 350 else 100,
                                delayMillis = if (isExpanded) 100 else 0,
                                easing = EaseIn
                            )
                        ).value
                    )
            )

        }
    }
}
