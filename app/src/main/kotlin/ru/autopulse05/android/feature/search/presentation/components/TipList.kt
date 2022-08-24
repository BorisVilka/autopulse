package ru.autopulse05.android.feature.search.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.feature.search.domain.model.SearchTip
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun TipList(
  searchTips: List<SearchTip>,
  didRequest: Boolean = false,
  onClick: (SearchTip) -> Unit
) {
  if (searchTips.isEmpty() && didRequest) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = PresentationText.Dynamic("Запрашиваемый артикул не найден").asString()
      )
    }
  } else {
    searchTips.forEach { tip ->
      TipItem(
        brand = tip.brand,
        number = tip.number,
        description = tip.description,
        onClick = { onClick(tip) }
      )

      Divider()
    }
  }
}

@Preview(
  name = "Tip List",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TipListPreview() {
  TipList(
    searchTips = listOf(
      SearchTip(
        brand = "brand",
        number = "number",
        description = "description"
      ),
    ),
    onClick = { },
  )
}