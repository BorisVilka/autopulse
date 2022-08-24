package ru.autopulse05.android.feature.user.presentation.profile.tabs

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.feature.user.presentation.profile.ProfileViewModel
import ru.autopulse05.android.feature.user.presentation.profile.components.OrderItem
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileEvent
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileUiEvent
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ProfileTabPageOrders(
  viewModel: ProfileViewModel = hiltViewModel()
) {
  val state = viewModel.state

  Column(modifier =  Modifier.background(color = MaterialTheme.colors.surface)) {
    state.orders.forEach { order ->
      OrderItem(
        item = order,
        onClick = {
          viewModel.onEvent(ProfileEvent.OrderDetail(value = it))
        }
      )
    }
  }
}

@Preview(
  name = "Profile Tab Page Orders",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileTabPageOrdersPreview() {
  ProfileTabPageOrders()
}