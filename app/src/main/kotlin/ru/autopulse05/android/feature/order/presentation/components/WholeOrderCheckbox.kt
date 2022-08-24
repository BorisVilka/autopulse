package ru.autopulse05.android.feature.order.presentation.components

import android.content.res.Configuration
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
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.order.domain.model.WholeOrderMode
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun WholeOrderCheckbox(
  value: WholeOrderMode,
  onClick: (WholeOrderMode) -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Checkbox(
      checked = value is WholeOrderMode.On,
      onCheckedChange = { value ->
        onClick(if (value) WholeOrderMode.On else WholeOrderMode.Off)
      }
    )
    Spacer(
      modifier = Modifier.width(8.dp)
    )
    Text(
      text = PresentationText.Resource(id = R.string.want_whole_order).asString()
    )
  }
}

@Preview(
  name = "Whole Order Checkbox",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun WholeOrderCheckboxPreview() {
  WholeOrderCheckbox(
    value = WholeOrderMode.On,
    onClick = {}
  )
}