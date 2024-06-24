package com.gautham.mafia.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.OffsetEffect
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gautham.mafia.R

val nico = FontFamily(Font(R.font.nico))
val komiko = FontFamily(Font(R.font.komika))
val akira = FontFamily(Font(R.font.akira))
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = komiko,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        shadow = Shadow(
            color = Color.Black.copy(alpha = 0.4f),
            offset = Offset(x=0f,y=7f),
            blurRadius = 5f

        )

    ),
    labelMedium = TextStyle(
        fontFamily = komiko,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        lineHeight = 28.sp,
        letterSpacing = 4.sp,
        /*shadow = Shadow(
            color = Color.Black.copy(alpha = 0.4f),
            offset = Offset(x=0f,y=7f),
            blurRadius = 5f

        )*/

    ),
    labelSmall = TextStyle(
        fontFamily = komiko,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 4.sp,
        /*shadow = Shadow(
            color = Color.Black.copy(alpha = 0.4f),
            offset = Offset(x=0f,y=7f),
            blurRadius = 5f

        )*/

    ),
    displaySmall = TextStyle(
        color = Color.Black,
        fontFamily = komiko,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        lineHeight = 28.sp,
        letterSpacing = 2.sp,
        shadow = Shadow(
            color = Color.Black.copy(alpha = 0.4f),
            offset = Offset(x=0f,y=7f),
            blurRadius = 5f

        )

    ),
    displayLarge = TextStyle(
        color = Color.Black,
        fontFamily = akira,
        fontWeight = FontWeight.Normal,
        fontSize = 50.sp,
        lineHeight = 28.sp,
        letterSpacing = 2.sp,
        shadow = Shadow(
            color = Color.Black.copy(alpha = 0.4f),
            offset = Offset(x=0f,y=7f),
            blurRadius = 5f

        )


    ),
    titleMedium = TextStyle(
        fontFamily = komiko,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        shadow = Shadow(
            color = Color.Black.copy(alpha = 0.4f),
            offset = Offset(x=0f,y=7f),
            blurRadius = 5f

        )

    )
        /*
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)