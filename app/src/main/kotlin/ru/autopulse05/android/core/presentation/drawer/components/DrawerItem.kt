package ru.autopulse05.android.core.presentation.drawer.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.drawer.util.DrawerItems


@Composable
fun DrawerItem(
  modifier: Modifier = Modifier,
  item: DrawerItems,
  expandedIndex: Int?,
  onBasicClick: (DrawerItems.LinkItem) -> Unit,
  onExpandableClick: (DrawerItems.ExpandableItem) -> Unit
) = when (item) {
  is DrawerItems.ExpandableItem -> DrawerExpandableItem(
    modifier = modifier,
    item = item,
    expanded = expandedIndex == item.index,
    onClick = onExpandableClick,
    onLinkClick = onBasicClick
  )
  is DrawerItems.LinkItem -> DrawerBasicItem(
    modifier = modifier,
    item = item,
    onClick = onBasicClick
  )
}

@Preview(
  name = "Drawer Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DrawerItemPreview() {
  DrawerItem(
    item = DrawerItems.AboutCompany,
    expandedIndex = null,
    onBasicClick = {},
    onExpandableClick = {}
  )
}
