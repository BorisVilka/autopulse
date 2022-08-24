package ru.autopulse05.android.shared.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun <T> RadioButtonWithTextList(
  values: List<Pair<PresentationText, T>>,
  onClick: (T) -> Unit,
  modifier: Modifier = Modifier,
  selectedValue: T?,
) {
  Column(modifier = modifier) {
    values.forEach { (text, value) ->
      RadioButtonWithText(text = text, value = value, onClick = onClick, selectedValue = selectedValue)
    }
  }
}

@Preview(
  name = "Radio Button With Text List",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RadioButtonWithTextListPreview() {
  RadioButtonWithTextList(
    values = listOf(
      Pair(PresentationText.Dynamic("Value 1"), "value 1"),
      Pair(PresentationText.Dynamic("Value 2"), "value 2"),
    ),
    onClick = {},
    selectedValue = "value 1",
  )
}