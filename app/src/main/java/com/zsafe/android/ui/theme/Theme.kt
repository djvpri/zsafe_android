package com.zsafe.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Teal = Color(0xFF0F9D8F)
private val TealDark = Color(0xFF0C7F74)
private val Ink = Color(0xFF14332F)
private val Background = Color(0xFFF4F7F6)

private val LightColors = lightColorScheme(
    primary = Teal,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE2F4F1),
    onPrimaryContainer = TealDark,
    background = Background,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
)

private val DarkColors = darkColorScheme(
    primary = Teal,
    onPrimary = Ink,
    background = Color(0xFF10201E),
    onBackground = Color(0xFFE0EFEC),
    surface = Color(0xFF1A2E2B),
    onSurface = Color(0xFFE0EFEC),
)

@Composable
fun ZsafeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
