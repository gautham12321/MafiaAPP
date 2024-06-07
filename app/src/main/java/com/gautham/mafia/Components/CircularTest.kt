package com.gautham.mafia.Components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import com.mafia2.data.PlayerDet
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun CircularLayout(
    modifier: Modifier = Modifier,
    players: List<PlayerDet>,       // Int for testing
    onClick: () -> Unit
) {
    Layout(
        modifier = modifier,
        content = {
            players.forEach {
                Profile(
                    size = getSizeProfile(players.size),
                    onClick = onClick,
                    playerdet = it
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
        val radiusReductionFactor = 2.0 / (1 + players.size * 0.1)
        val radius = (baseRadius * radiusReductionFactor).toInt()

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
    return 110f / (size.toFloat() / 10f)-20f
}



