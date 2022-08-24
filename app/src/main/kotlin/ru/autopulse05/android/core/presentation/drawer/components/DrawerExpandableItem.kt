package ru.autopulse05.android.core.presentation.drawer.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.core.presentation.drawer.util.DrawerItems
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.shared.presentation.components.ExpandableCard

@Composable
fun DrawerExpandableItem(
  modifier: Modifier = Modifier,
  item: DrawerItems.ExpandableItem,
  onClick: (DrawerItems.ExpandableItem) -> Unit,
  onLinkClick: (DrawerItems.LinkItem) -> Unit,
  expanded: Boolean,
) = ExpandableCard(
  modifier = modifier,
  title = item.header,
  onClick = { onClick(item) },
  elevationEnabled = false,
  expanded = expanded,
  shape = RoundedCornerShape(0.dp),
  textRowExpandedBackgroundColor = Color.BrandYellow
) {
  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    item.children.forEach { children ->
      DrawerBasicItem(
        item = children,
        onClick = onLinkClick
      )
    }
  }
}


@Preview(
  name = "Drawer Expandable Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DrawerExpandableItemPreview() {
  DrawerExpandableItem(
    item = DrawerItems.Profile,
    expanded = false,
    onClick = {},
    onLinkClick = {}
  )
}

@Preview(
  name = "Drawer Expandable Item Expanded",
  showBackground = true
)
@Composable
fun DrawerExpandableItemExpandedPreview() {
  DrawerExpandableItem(
    item = DrawerItems.Profile,
    expanded = true,
    onClick = { },
    onLinkClick = {}
  )
}