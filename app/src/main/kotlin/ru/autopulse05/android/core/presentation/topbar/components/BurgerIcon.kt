package ru.autopulse05.android.core.presentation.topbar.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun BurgerIcon(
  onClick: () -> Unit
) {
  IconButton(onClick = { onClick() }) {
    Icon(
      imageVector = Icons.Default.Menu,
      contentDescription = PresentationText.Resource(id = R.string.navigation_drawer).asString()
    )
  }
}

@Preview
@Composable
fun BurgerPreview() {
  BurgerIcon(onClick = {})
}