package ru.autopulse05.android.shared.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraSmall
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun MarkedListItem(
  text: PresentationText,
  textColor: Color = MaterialTheme.colors.onSecondary
) {
  Row(Modifier.padding(SpaceExtraSmall), verticalAlignment = Alignment.CenterVertically) {
    Canvas(
      modifier = Modifier
        .padding(start = SpaceSmall, end = SpaceSmall)
        .size(SpaceExtraSmall)
    ) {
      drawCircle(textColor)
    }
    Text(text = text.asString(), color = textColor)
  }
}

@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Marked List Item",
  showBackground = true
)
@Composable
fun MarkedListItemPreview() {
  MarkedListItem(
    text = PresentationText.Dynamic("first")
  )
}