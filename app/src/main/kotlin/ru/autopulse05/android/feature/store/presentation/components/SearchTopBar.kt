package ru.autopulse05.android.feature.store.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.search.presentation.components.SearchBar
import ru.autopulse05.android.shared.presentation.components.Burger

@Composable
fun SearchTopBar(
  onSearchBarClick: () -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .background(color = MaterialTheme.colors.secondary)
      .padding(SpaceNormal)
  ) {
    SearchBar(
      value = "",
      onValueChange = { },
      hint = stringResource(id = R.string.detail_number),
      enabled = false,
      modifier = Modifier.clickable { onSearchBarClick() }
    )
  }
}

@Preview(
  name = "VehicleSearch Top Bar",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SearchTopBarPreview() {
  SearchTopBar(
    onSearchBarClick = { }
  )
}