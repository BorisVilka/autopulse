package ru.autopulse05.android.core.presentation.topbar.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.R
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ProfileIcon(
  onClick: () -> Unit
) {
  IconButton(onClick = { onClick() }) {
    Icon(
      painter = painterResource(id = R.drawable.ic_profile),
      modifier = Modifier.width(20.dp).height(20.dp),
      contentDescription = PresentationText.Resource(id = R.string.profile).asString()
    )
  }
}

@Preview
@Composable
fun ProfileIconPreview() {
  ProfileIcon(onClick = {})
}