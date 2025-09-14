package com.example.icare.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import java.util.Calendar

enum class ThemeMode { AUTO, LIGHT, DARK }

@Composable
fun ICareTheme(
    themeMode: ThemeMode = ThemeMode.AUTO,
    content: @Composable () -> Unit
) {
    val isNight = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.AUTO -> isNightByTime()
    }

    val lightColors = lightColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFF7E57C2), // purple
        secondary = androidx.compose.ui.graphics.Color(0xFF26C6DA), // teal
        background = androidx.compose.ui.graphics.Color(0xFFFFFBFE),
        surface = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
    )

    val darkColors = darkColorScheme(
        primary = NeonPurple,
        secondary = NeonTeal,
        background = PureBlack,
        surface = NightSurface
    )

    MaterialTheme(
        colorScheme = if (isNight) darkColors else lightColors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}

private fun isNightByTime(): Boolean {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return (hour < 6) || (hour >= 18) // 6pm–6am = dark
}
