package ru.autopulse05.android.feature.store.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.store.presentation.util.MenuItems
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun Menu(
  title: PresentationText,
  items: List<MenuItems>,
  onItemClick: (MenuItems) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
  ) {
    Text(
      text = title.asString(),
      style = MaterialTheme.typography.h1,
      modifier = Modifier.padding(start = SpaceNormal)
    )

    items.forEach {
      MenuItem(
        icon = painterResource(id = it.icon),
        title = it.title,
        description = it.description,
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onItemClick(it) }
          .padding(SpaceNormal)
      )
      Divider()
    }
  }
}

@Preview(
  name = "Menu",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MenuPreview() {
  val items = listOf(
    MenuItems.OriginalCatalogue,
    MenuItems.Catalogue,
    MenuItems.RequestByVin
  )

  Menu(
    title = PresentationText.Resource(R.string.spare_parts),
    items = items,
    onItemClick = { }
  )
}