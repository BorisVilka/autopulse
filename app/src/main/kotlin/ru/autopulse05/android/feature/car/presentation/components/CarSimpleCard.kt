package ru.autopulse05.android.feature.car.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall


@Composable
fun CarSimpleCard(
  name: String,
  brand: String?,
  modifier: Modifier = Modifier,
  onClick: () -> Unit = {},
) {
  Column(
    modifier = modifier
      .clickable { onClick() }
      .shadow(elevation = SpaceSmall)
      .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
      .padding(SpaceNormal)
      .fillMaxWidth()
  ) {
    Text(
      text = name,
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    Text(text = brand ?: "")
  }
}

@Preview(
  name = "Car Simple Card",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CarSimpleCardPreview() {
  CarSimpleCard(
    name = "name",
    brand = "brand",
  )
}