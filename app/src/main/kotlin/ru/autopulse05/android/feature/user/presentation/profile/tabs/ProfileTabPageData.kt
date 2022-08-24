package ru.autopulse05.android.feature.user.presentation.profile.tabs

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.user.presentation.profile.ProfileViewModel
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ProfileTabPageData(
  viewModel: ProfileViewModel = hiltViewModel()
) {
  val state = viewModel.state

  if (state.user != null) Column(modifier =  Modifier.background(color = MaterialTheme.colors.surface)) {
    Text(
      text = PresentationText.Resource(id = R.string.name).asString(),
      modifier = Modifier.padding(top = SpaceNormal, bottom = SpaceSmall),
      style = MaterialTheme.typography.subtitle1
    )

    TextField(
      modifier = Modifier.fillMaxWidth(),
      value = state.user.name,
      enabled = false,
      onValueChange = {}
    )

    Text(
      text = PresentationText.Resource(id = R.string.phone).asString(),
      modifier = Modifier.padding(top = SpaceNormal, bottom = SpaceSmall),
      style = MaterialTheme.typography.subtitle1
    )

    TextField(
      modifier = Modifier.fillMaxWidth(),
      value = state.user.mobile,
      enabled = false,
      onValueChange = {}
    )

    Text(
      text = PresentationText.Resource(id = R.string.email).asString(),
      modifier = Modifier.padding(top = SpaceNormal, bottom = SpaceSmall),
      style = MaterialTheme.typography.subtitle1
    )

    TextField(
      modifier = Modifier.fillMaxWidth(),
      value = state.user.email,
      enabled = false,
      onValueChange = {}
    )
  }
}

@Preview(
  name = "Profile Tab Page Data",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileTabPageDataPreview() {
  ProfileTabPageData()
}