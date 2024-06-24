package com.gautham.mafia.Components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import com.gautham.mafia.Data.Avatar
import com.gautham.mafia.R
import com.gautham.mafia.ui.theme.Green_M
import com.mafia2.data.Player
import com.mafia2.data.PlayerDet
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun CircularLayout(
    modifier: Modifier = Modifier,
    gameOverScreen:Boolean=false,

    players: List<Player> = listOf(
        Player(
            id = 0,
            name = "Gautham",
            avatar = Avatar(R.drawable._043232_avatar_batman_comics_hero_icon)
        ),
        Player(
            id = 0,
            name = "Gautham",
            avatar = Avatar(R.drawable._043232_avatar_batman_comics_hero_icon)
        ),
        Player(
            id = 0,
            name = "Gautham",
            avatar = Avatar(R.drawable._043232_avatar_batman_comics_hero_icon)
        ),
        Player(
            id = 0,
            name = "Gautham",
            avatar = Avatar(R.drawable._043232_avatar_batman_comics_hero_icon)
        ),
    ),
    // Int for testing
    onClick: (Player) -> Unit = {},
    selectedPlayer: Player? = null,
    radius: Float
) {
    Layout(
        modifier = modifier,
        content = {
            players.forEach {
                Profile(
                    size = getSizeProfile(players.size),
                    onClick = {onClick(it)},
                    playerdet = PlayerDet(it.name,it.avatar),eliminated = !it.isAlive
               , color = if(selectedPlayer?.id == it.id || gameOverScreen) Green_M else Color.Black
                )
            }
        }
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val maxElementSize = placeables.maxOfOrNull { min(it.width, it.height) } ?: 0

        // Adjust radius based on the number of players
        val baseRadius = (min(constraints.maxWidth, constraints.maxHeight) - maxElementSize) / 2
   //     val radiusReductionFactor = 2.0 / (1 + players.size * 0.1)
        val radius =min(baseRadius,radius.toInt())  //* radiusReductionFactor).toInt()

        layout(constraints.maxWidth, constraints.maxHeight) {
            val centerX = constraints.maxWidth / 2
            val centerY = constraints.maxHeight / 2

            placeables.forEachIndexed { index, placeable ->
                val angle = 2.0 * PI * index / placeables.size
                val x = (centerX + radius * cos(angle) - placeable.width / 2).toInt()
                val y = (centerY + radius * sin(angle) - placeable.height / 2).toInt()

                placeable.placeRelative(x, y)
            }
        }
    }
}

fun getSizeProfile(size: Int): Float {
    return  ((15f/size.toFloat()) *30f)//FIXXXXXXX
}//assuming max of 15 players in a match



