package ru.autopulse05.android.feature.search.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection

@Composable
fun TipItem(
  brand: String,
  number: String,
  description: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .clickable { onClick() }
      .fillMaxWidth()
      .padding(SpaceNormal)
  ) {
    BrandNumberSection(
      brand = brand,
      number = number
    )

    if (description.isNotEmpty()) {
      Spacer(modifier = Modifier.height(SpaceNormal))

      Text(
        text = description,
        style = MaterialTheme.typography.body2
      )
    }
  }
}

@Preview(
  name = "Tip Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TipItemPreview() {
  TipItem(
    brand = "brand",
    number = "number",
    description = "description",
    onClick = {}
  )
}