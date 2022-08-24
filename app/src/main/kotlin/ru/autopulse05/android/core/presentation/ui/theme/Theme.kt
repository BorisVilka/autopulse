package ru.autopulse05.android.core.presentation.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
  primary = Color.BrandYellow,
  secondary = Color.BrandDarkGray,
  background = Color.BrandLightBlack,
  surface = Color.BrandBlack,
  onSecondary = Color.BrandWhite
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
  primary = Color.BrandYellow,
  secondary = Color.BrandWhite,
  background = Color.BrandLightGray,
  surface = Color.BrandWhite,
  onSecondary = Color.BrandBlack
  /* Other default colors to override
  surface = Color.White,
  onPrimary = Color.White,
  onSecondary = Color.Black,
  onBackground = Color.Black,
  onSurface = Color.Black,
  */
)

@Composable
fun Autopulse05Theme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}