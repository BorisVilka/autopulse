package ru.autopulse05.android.core.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.autopulse05.android.R

val montserrat = FontFamily(
  Font(R.font.montserrat_extra_light, FontWeight.ExtraLight),
  Font(R.font.montserrat_light, FontWeight.Light),
  Font(R.font.montserrat_thin, FontWeight.Thin),
  Font(R.font.montserrat_regular, FontWeight.Normal),
  Font(R.font.montserrat_medium, FontWeight.Medium),
  Font(R.font.montserrat_semi_bold, FontWeight.SemiBold),
  Font(R.font.montserrat_bold, FontWeight.Bold),
  Font(R.font.montserrat_extra_bold, FontWeight.ExtraBold),
  Font(R.font.montserrat_black, FontWeight.Black)
)

val Typography = Typography(
  defaultFontFamily = montserrat,
  body1 = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
  ),
  h1 = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    color = Color.BrandYellow
  ),
  h2 = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
  ),
  h3 = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
  ),
  button = TextStyle(
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp
  ),
  subtitle1 = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    color = Color.BrandYellow
  ),
  subtitle2 = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    color = Color.BrandGreen
  ),
/* Other default badgeText styles to override
button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
),
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)