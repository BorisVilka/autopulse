package ru.autopulse05.android.shared.presentation.components

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun MarkedList(
  items: List<PresentationText>,
  textColor: Color = MaterialTheme.colors.onSecondary
) {
  items.forEach { text ->
    MarkedListItem(text = text, textColor)
  }
}

@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Marked List",
  showBackground = true
)
@Composable
fun MarkedListPreview() {
  MarkedList(
    items = listOf(
      PresentationText.Dynamic("first;"),
      PresentationText.Dynamic("second.")
    )
  )
}