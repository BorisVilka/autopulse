package ru.autopulse05.android.feature.user.presentation.profile.tabs

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.user.presentation.profile.ProfileViewModel
import ru.autopulse05.android.feature.user.presentation.profile.components.ComplaintItem

@Composable
fun ProfileTabPageComplaints(
  viewModel: ProfileViewModel = hiltViewModel()
) {
  val state = viewModel.state

  Column(
    modifier = Modifier.padding(SpaceNormal)
  ) {


  }
}

@Preview(
  name = "Profile Tab Page Complaints",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileTabPageComplaintsPreview() {
  ProfileTabPageComplaints()
}