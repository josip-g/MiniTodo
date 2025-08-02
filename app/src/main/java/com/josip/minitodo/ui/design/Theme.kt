package com.josip.minitodo.ui.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Green80,
    secondary = GreenGrey80,
    tertiary = Mint80
)

private val LightColorScheme = lightColorScheme(
    primary = Green40,
    secondary = GreenGrey40,
    tertiary = Mint40,
    background = Color(0xFFF0FFF4),
    surface = Color(0xFFE8F5E9),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color(0xFF1B5E20),
    onSurface = Color(0xFF1B5E20)
)

@Composable
fun MiniTodoTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme(
        primary = Color(0xFF388E3C),
        onPrimary = Color.White,
        secondary = Color(0xFF66BB6A),
        onSecondary = Color.Black,
        tertiary = Color(0xFFA5D6A7),
        background = Color(0xFFF1F8E9),
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,

        primaryContainer = Color(0xFFC8E6C9),
        onPrimaryContainer = Color(0xFF1B5E20),
        secondaryContainer = Color(0xFFA5D6A7),
        onSecondaryContainer = Color(0xFF1B5E20)
    )


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}