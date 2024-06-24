package com.gautham.mafia.Extras

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import android.graphics.BlurMaskFilter
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable

import androidx.compose.ui.draw.drawBehind

import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
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
fun copyToClipboard(context: Context, text: String) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("RoomID", text)
    clipboardManager.setPrimaryClip(clip)
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
    R.drawable._043275_avatar_man_person_punk_icon,
    R.drawable.bob,
    R.drawable.duck,
    R.drawable.duck2jpg,
    R.drawable.fox,
    R.drawable.sad,
    R.drawable.sideye,
    R.drawable.sus,
    R.drawable.wolf, R.drawable._17b09161d5d538c61a5716bfa7dc172,
    R.drawable._181b0a103e6a22270d3449ebb54c322, R.drawable._189cb2f9a80b85c3b133efa3a4e6e55,
    R.drawable._49b46ea804e07f5fba7ad425bb41023, R.drawable._53a24a44c7df0689720d69ed15f8db1,
    R.drawable._607f88226129a9f6906c68b1c521131, R.drawable._7deed22da91d3a463a3921738a5f98c,
    R.drawable._c486a070ddf3e02457c6100c0bef8cd, R.drawable._ea277a2c6fc36245f0cf0c188f2228f,
    R.drawable.ae1c741effe77f0a31130c4ba72d575a, R.drawable.d41ddd0e6fa2f83709d24fc66fcd515c,
    R.drawable.d702635806a16ab88cede1a72548a5fa,



)
var previous = 0
fun getRandomAvatarImage(): Avatar {
    var randomIndex=0
    do {
      randomIndex = avatarImages.indices.random()
 }while (previous==randomIndex)
 previous=randomIndex

    return Avatar(avatarImages[randomIndex])


}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    data:Int=R.drawable.thumbsdown_office
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = data).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null, modifier = modifier

    )}


