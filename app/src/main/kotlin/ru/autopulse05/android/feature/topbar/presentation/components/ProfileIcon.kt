package ru.autopulse05.android.feature.topbar.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ProfileIcon(
  onClick: () -> Unit
) {
  IconButton(onClick = { onClick() }) {
    Icon(
      imageVector = Icons.Default.Person,
      contentDescription = PresentationText.Resource(id = R.string.profile).asString()
    )
  }
}

@Preview
@Composable
fun ProfileIconPreview() {
  ProfileIcon(onClick = {})
}