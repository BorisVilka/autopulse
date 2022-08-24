package ru.autopulse05.android.feature.search.presentation.components

import android.content.res.Configuration
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.feature.search.domain.model.HistoryItem

@Composable
fun HistoryItemList(
  historyItems: List<HistoryItem>,
  onClick: (HistoryItem) -> Unit
) {
  historyItems.forEach { item ->
    HistoryItem(
      brand = item.brand,
      number = item.number,
      description = item.description,
      onClick = { onClick(item) }
    )

    Divider()
  }
}

@Preview(
  name = "History Item List",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HistoryItemListPreview() {
  HistoryItemList(
    historyItems = listOf(
      HistoryItem(
        brand = "brand",
        number = "number",
        description = "description",
        numberFix = "numberFix",
        datetime = "datetime"
      ),
    ),
    onClick = { },
  )
}