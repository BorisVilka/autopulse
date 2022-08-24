package ru.autopulse05.android.shared.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun <T> RadioButtonWithText(
  text: PresentationText,
  value: T,
  onClick: (T) -> Unit,
  modifier: Modifier = Modifier,
  selectedValue: T?,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .selectable(
        selected = (text == selectedValue),
        onClick = { onClick(value) }
      ),
    verticalAlignment = Alignment.CenterVertically
  ) {
    RadioButton(
      selected = (value == selectedValue),
      onClick = { onClick(value) }
    )
    Text(
      text = text.asString(),
      style = MaterialTheme.typography.body1,
      modifier = Modifier.padding(start = SpaceSmall)
    )
  }
}

@Preview(
  name = "Radio Button With Text",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RadioButtonWithTextPreview() {
  RadioButtonWithText(
    text = PresentationText.Dynamic("Value"),
    value = "value",
    onClick = {},
    selectedValue = "value",
  )
}