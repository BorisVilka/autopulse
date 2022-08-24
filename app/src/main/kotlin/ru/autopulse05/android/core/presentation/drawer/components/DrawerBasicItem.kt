package ru.autopulse05.android.core.presentation.drawer.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.core.presentation.drawer.util.DrawerItems
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall


@Composable
fun DrawerBasicItem(
  modifier: Modifier = Modifier,
  item: DrawerItems.LinkItem,
  onClick: (DrawerItems.LinkItem) -> Unit
) {
  Text(
    modifier = modifier
      .fillMaxWidth()
      .padding(SpaceSmall)
      .clickable {
        onClick(item)
      },
    text = item.header.asString(),
    color = MaterialTheme.colors.onSecondary,
    style = MaterialTheme.typography.body1
  )
}

@Preview(
  name = "Drawer Basic Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DrawerBasicItemPreview() {
  DrawerBasicItem(
    item = DrawerItems.AboutCompany,
    onClick = {}
  )
}
