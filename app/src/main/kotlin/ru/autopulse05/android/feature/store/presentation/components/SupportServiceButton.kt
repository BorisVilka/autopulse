package ru.autopulse05.android.feature.store.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun SupportServiceButton(
  icon: Painter,
  title: PresentationText,
  modifier: Modifier = Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
  ) {
    Icon(
      painter = icon,
      contentDescription = null
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    Text(
      text = title.asString(),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.subtitle1
    )
  }
}

@Preview(
  name = "Support Service Button",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SupportServiceButtonPreview() {
  SupportServiceButton(
    icon = painterResource(id = R.drawable.ic_phone),
    title = PresentationText.Resource(R.string.call)
  )
}