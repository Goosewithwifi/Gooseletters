package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = EditorialBurgundy, // Terracotta/coral accent as the active/primary color
    onPrimary = EditorialWhite,
    secondary = EditorialMuted,
    onSecondary = EditorialWhite,
    background = EditorialBackground, // Creamy milky off-white
    onBackground = EditorialBlack, // Soft charcoal
    surface = MilkyCardBg, // Pure milk white
    onSurface = EditorialBlack,
    surfaceVariant = Color(0xFFF3EDE4), // Creamy dark variant for inputs/containers
    onSurfaceVariant = EditorialBlack,
    outline = EditorialBorder,
    error = EditorialBurgundy
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFF5835E), // Brighter pastel peach for dark mode
    onPrimary = Color(0xFF2B2927),
    secondary = Color(0xFFAEA79E),
    onSecondary = Color(0xFF2B2927),
    background = Color(0xFF1E1C1A), // Rich warm charcoal/dark cocoa
    onBackground = Color(0xFFECE6DF), // Warm creamy white text
    surface = Color(0xFF2B2826), // Soft warm dark cards
    onSurface = Color(0xFFECE6DF),
    surfaceVariant = Color(0xFF3B3734),
    onSurfaceVariant = Color(0xFFECE6DF),
    outline = Color(0xFF423E3A),
    error = Color(0xFFF5835E)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
