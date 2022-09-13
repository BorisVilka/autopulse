package ru.autopulse05.android.feature.product.presentation.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal

@Composable
fun CrosseItem(
  onClick: () -> Unit,
  brand: String,
  number: String
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .clip(shape = MaterialTheme.shapes.medium)
      .clickable { onClick() }
      .padding(SpaceNormal)
      .fillMaxWidth()
  ) {
    Text(
      text = brand,
      style = MaterialTheme.typography.subtitle1,
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    Text(text = number, textAlign = TextAlign.Center)
  }
}

@Preview(
  name = "Crosse Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CrosseItemPreview() {
  CrosseItem(
    onClick = {},
    brand = "brand",
    number = "number"
  )
}