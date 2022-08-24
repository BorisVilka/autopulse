package ru.autopulse05.android.shared.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal

@Composable
fun BrandNumberSection(
  brand: String,
  number: String,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
  ) {
    Text(
      text = brand,
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.width(SpaceNormal))

    Text(text = number)
  }
}

@Preview
@Composable
fun BrandNumberSectionPreview() {
  BrandNumberSection(
    brand = "brand",
    number = "number"
  )
}