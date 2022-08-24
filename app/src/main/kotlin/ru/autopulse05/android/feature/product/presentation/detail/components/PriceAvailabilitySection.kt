package ru.autopulse05.android.feature.product.presentation.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun PriceAvailabilitySection(
  price: String,
  availability: String,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
  ) {
    Text(
      text = with(AnnotatedString.Builder()) {
        withStyle(SpanStyle(color = Color.BrandYellow)) {
          append("${PresentationText.Resource(R.string.price).asString()} ")
        }
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
          append(price.toDouble().toInt().toString())
          append(" ${PresentationText.Resource(R.string.ruble).asString()}")
        }
        toAnnotatedString()
      }
    )

    Spacer(modifier = Modifier.width(SpaceNormal))

    Text(
      text = with(AnnotatedString.Builder()) {
        withStyle(SpanStyle(color = Color.BrandYellow)) {
          append(PresentationText.Resource(R.string.availability).asString())
        }
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
          append(
            when (availability.toInt()) {
              -1 -> " +"
              -2 -> " ++"
              -3 -> " +++"
              -10 -> " ${PresentationText.Resource(R.string.not_available).asString()}"
              else -> " $availability " + PresentationText.Resource(R.string.pieces).asString()
            }
          )
        }
        toAnnotatedString()
      }
    )
  }
}

@Preview(
  name = "Price Availability Section",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PriceAvailabilitySectionPreview() {
  PriceAvailabilitySection(
    price = "price",
    availability = "availability"
  )
}