package ru.autopulse05.android.feature.store.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun SupportServiceButtons(
  onCallClick: () -> Unit,
  onWriteClick: () -> Unit
) {
  Row {
    SupportServiceButton(
      icon = painterResource(id = R.drawable.ic_phone),
      title = PresentationText.Resource(R.string.call),
      modifier = Modifier
        .clip(shape = MaterialTheme.shapes.medium)
        .clickable {
          onCallClick()
        }
        .padding(SpaceSmall)
    )

    Spacer(modifier = Modifier.width(SpaceExtraLarge))

    SupportServiceButton(
      icon = painterResource(id = R.drawable.ic_letter),
      title = PresentationText.Resource(R.string.write_a_letter),
      modifier = Modifier
        .clip(shape = MaterialTheme.shapes.medium)
        .clickable {
          onWriteClick()
        }
        .padding(SpaceSmall)
    )
  }
}

@Preview(
  name = "Support Service Buttons",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SupportServiceButtonsPreview() {
  SupportServiceButtons(
    onCallClick = {},
    onWriteClick = {}
  )
}