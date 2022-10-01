package ru.autopulse05.android.feature.store.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraSmall
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun MenuItem(
  icon: Painter,
  title: PresentationText,
  description: PresentationText,
  modifier: Modifier = Modifier
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
  ) {
    Image(
      painter = icon,
      contentDescription = null,
      modifier = Modifier.size(32.dp)
    )

    Spacer(modifier = Modifier.width(SpaceNormal))

    Column {
      Text(
        text = title.asString(),
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onSecondary
      )

      Spacer(modifier = Modifier.height(SpaceExtraSmall))

      Text(
        text = description.asString()
      )
    }
  }
}

@Preview(
  name = "Menu Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MenuItemPreview() {
  MenuItem(
    icon = painterResource(id = R.drawable.ic_wrench),
    title = PresentationText.Resource(R.string.original_catalogue),
    description = PresentationText.Resource(R.string.search_by_vin),
  )
}

