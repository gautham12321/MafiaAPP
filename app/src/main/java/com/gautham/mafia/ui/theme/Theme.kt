package com.gautham.mafia.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
private val MafiaColorScheme= darkColorScheme(

    primary = Red_M,
    secondary = Grey_M,
    tertiary = Black_M,
    onPrimary = Black_M,
    surfaceVariant = Grey_M,
    onSurfaceVariant = Black_M,
    onSurface = Black_M,
    primaryContainer = Grey_M




)



private val MafiaShapes = Shapes(


    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(20),
    large = RoundedCornerShape(0.dp)
)
@Composable
fun MafiaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =
    MaterialTheme(
        shapes = MafiaShapes ,
        colorScheme = MafiaColorScheme,
        typography = Typography,
        content = content
    )
}