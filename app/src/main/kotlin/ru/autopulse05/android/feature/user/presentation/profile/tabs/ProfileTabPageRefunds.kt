package ru.autopulse05.android.feature.user.presentation.profile.tabs

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.feature.user.presentation.profile.ProfileViewModel
import ru.autopulse05.android.feature.user.presentation.profile.components.RefundItem

@Composable
fun ProfileTabPageRefunds(
  viewModel: ProfileViewModel = hiltViewModel()
) {
  val state = viewModel.state

  Column {
    state.refunds.forEach { complaint ->
      RefundItem(refund = complaint)
    }
  }
}

@Preview(
  name = "Profile Tab Page Refunds",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileTabPageRefundsPreview() {
  ProfileTabPageRefunds()
}