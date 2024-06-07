package com.gautham.mafia.Extras

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import android.graphics.BlurMaskFilter
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable

import androidx.compose.ui.draw.drawBehind

import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.gautham.mafia.Data.Avatar
import com.gautham.mafia.R


fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)

 @Composable
 fun Icons.Copy() : ImageVector {
     val copyIcon = ImageVector.vectorResource(id = R.drawable.copyicon)
     return copyIcon
 }


val avatarImages = listOf(
    R.drawable._043229_afro_avatar_male_man_icon,
    R.drawable._043232_avatar_batman_comics_hero_icon,
    R.drawable._043243_actor_chaplin_comedy_man_icon,
    R.drawable._043245_avatar_coffee_cup_zorro_icon,
    R.drawable._043255_beard_hipster_male_man_icon,
    R.drawable._043256_indian_male_man_person_icon,
    R.drawable._043257_indian_man_sikh_turban_icon,
    R.drawable._043260_avatar_male_man_portrait_icon,
    R.drawable._043262_avatar_man_muslim_icon,
    R.drawable._043265_male_man_old_portrait_icon,
    R.drawable._043267_fighter_luchador_man_wrestler_icon,
    R.drawable._043271_avatar_bug_insect_spider_icon,
    R.drawable._043272_avatar_lazybones_sloth_sluggard_icon,
    R.drawable._043275_avatar_man_person_punk_icon


)
fun getRandomAvatarImage(): Avatar {

    val randomIndex = (0 until avatarImages.size).random()
    return Avatar(avatarImages[randomIndex])


}