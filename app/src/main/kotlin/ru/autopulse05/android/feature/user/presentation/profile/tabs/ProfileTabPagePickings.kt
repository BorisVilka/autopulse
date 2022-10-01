package ru.autopulse05.android.feature.user.presentation.profile.tabs

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.feature.user.presentation.profile.ProfileViewModel
import ru.autopulse05.android.feature.user.presentation.profile.components.PickingItem

@Composable
fun ProfileTabPagePickings(
  viewModel: ProfileViewModel = hiltViewModel()
) {
  val state = viewModel.state

  Column {

  }
}

@Preview(
  name = "Profile Tab Page Pickings",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileTabPagePickingsPreview() {
  ProfileTabPagePickings()
}