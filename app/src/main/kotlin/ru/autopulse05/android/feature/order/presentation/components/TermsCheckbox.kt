package ru.autopulse05.android.feature.order.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun TermsCheckbox(
  value: Boolean,
  onClick: (Boolean) -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth().clickable {
      onClick(!value)
    },
    verticalAlignment = Alignment.CenterVertically
  ) {
    Checkbox(
      checked = value,
      onCheckedChange = null
    )
    Spacer(
      modifier = Modifier.width(SpaceSmall)
    )
    Text(
      text = PresentationText.Resource(id = R.string.agreed_with_terms).asString()
    )
  }
}

@Preview(
  name = "Terms Checkbox",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TermsCheckboxPreview() {
  TermsCheckbox(
    value = true,
    onClick = {}
  )
}