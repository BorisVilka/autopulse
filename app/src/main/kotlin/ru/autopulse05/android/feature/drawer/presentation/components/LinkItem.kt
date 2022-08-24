package ru.autopulse05.android.feature.drawer.presentation.components

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
import ru.autopulse05.android.feature.drawer.presentation.util.DrawerItems


@Composable
fun LinkItem(
  modifier: Modifier = Modifier,
  item: DrawerItems.LinkItem,
  onClick: (DrawerItems.LinkItem) -> Unit
) {
  Text(
    modifier = modifier
      .fillMaxWidth()
      .padding(
        vertical = 8.dp,
        horizontal = 12.dp
      )
      .clickable {
        onClick(item)
      },
    text = item.header.asString(),
    color = MaterialTheme.colors.onSecondary,
    style = MaterialTheme.typography.body1
  )
}

@Preview(
  name = "Drawer Link Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LinkItemPreview() {
  LinkItem(
    item = DrawerItems.AboutCompany,
    onClick = {}
  )
}
