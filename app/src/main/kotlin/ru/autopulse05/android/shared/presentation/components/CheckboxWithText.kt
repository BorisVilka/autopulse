package ru.autopulse05.android.shared.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun CheckboxWithText(
  text: PresentationText,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  checked: Boolean,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clickable {
        onClick()
      },
    verticalAlignment = Alignment.CenterVertically
  ) {
    Checkbox(
      checked = checked,
      colors = CheckboxDefaults.colors(
        checkedColor = Color.BrandYellow
      ),
      onCheckedChange = { onClick() }
    )
    Text(
      text = text.asString(),
      style = MaterialTheme.typography.body1,
      modifier = Modifier.padding(start = SpaceSmall)
    )
  }
}

@Preview(
  name = "Checkbox With Text",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CheckboxWithTextPreview() {
  CheckboxWithText(
    text = PresentationText.Dynamic("Value"),
    onClick = {},
    checked = false
  )
}

@Preview(
  name = "Checkbox With Text Checked",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CheckboxWithTextCheckedPreview() {
  CheckboxWithText(
    text = PresentationText.Dynamic("Value"),
    onClick = {},
    checked = true
  )
}