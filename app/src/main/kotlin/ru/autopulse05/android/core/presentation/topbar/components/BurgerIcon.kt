package ru.autopulse05.android.core.presentation.topbar.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun BurgerIcon(
  onClick: () -> Unit
) {
  IconButton(onClick = { onClick() }) {
    Icon(
      imageVector = Icons.Default.Menu,
      tint = Color.BrandYellow,
      modifier = Modifier.width(25.dp).height(25.dp),
      contentDescription = PresentationText.Resource(id = R.string.navigation_drawer).asString()
    )
  }
}

@Preview
@Composable
fun BurgerPreview() {
  BurgerIcon(onClick = {})
}