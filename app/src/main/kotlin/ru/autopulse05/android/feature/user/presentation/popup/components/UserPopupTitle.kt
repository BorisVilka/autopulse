package ru.autopulse05.android.feature.user.presentation.popup.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun UserPopupTitle(id: String, name: String, modifier: Modifier = Modifier) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = PresentationText.Resource(id = R.string.personal_cabinet).asString(),
      style = MaterialTheme.typography.subtitle1,
      color = MaterialTheme.colors.onSecondary,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    Row {
      Text(
        text = id,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onSecondary
      )

      Spacer(modifier = Modifier.width(SpaceSmall))

      Text(
        text = name,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onSecondary
      )
    }
  }
}

@Preview
@Composable
fun UserPopupTitlePreview() {
  UserPopupTitle(
    id = "82877478",
    name = "Test"
  )
}