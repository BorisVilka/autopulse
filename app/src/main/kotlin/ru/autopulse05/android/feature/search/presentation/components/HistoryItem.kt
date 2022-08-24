package ru.autopulse05.android.feature.search.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection

@Composable
fun HistoryItem(
  brand: String,
  number: String,
  description: String?,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(modifier = Modifier
    .fillMaxWidth()
    .padding(SpaceNormal)
    .clickable { onClick() }) {
    Column(modifier = modifier.fillMaxWidth()) {
      BrandNumberSection(
        brand = brand,
        number = number
      )

      if (description?.isNotEmpty() == true) {
        Spacer(modifier = Modifier.height(SpaceNormal))

        Text(
          text = description,
          style = MaterialTheme.typography.body2
        )
      }
    }

    Icon(
      imageVector = Icons.Default.Refresh,
      contentDescription = "История"
    )
  }
}

@Preview(
  name = "History Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HistoryItemPreview() {
  HistoryItem(
    brand = "brand",
    number = "number",
    description = "description",
    onClick = {}
  )
}