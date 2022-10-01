package ru.autopulse05.android.feature.user.presentation.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabState
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabs
import ru.autopulse05.android.feature.user.presentation.profile.util.rememberProfileTabState

@Composable
fun ProfileTabLayout(
  onClick: (ProfileTabs) -> Unit,
  profileTabState: ProfileTabState
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Row(modifier = Modifier.fillMaxWidth()) {
      ProfileTab(
        modifier = Modifier.weight(0.33f),
        tab = ProfileTabs.Orders,
        profileTabState = profileTabState,
        onCLick = onClick
      )
      Spacer(modifier = Modifier.width(SpaceSmall))

      ProfileTab(
        modifier = Modifier.weight(0.33f),
        tab = ProfileTabs.Payments,
        profileTabState = profileTabState,
        onCLick = onClick
      )
    }

    Row(modifier = Modifier.fillMaxWidth()) {
      ProfileTab(
        modifier = Modifier.weight(0.5f),
        tab = ProfileTabs.Garage,
        profileTabState = profileTabState,
        onCLick = onClick
      )

      Spacer(modifier = Modifier.width(SpaceSmall))

      ProfileTab(
        modifier = Modifier.weight(0.5f),
        tab = ProfileTabs.Data,
        profileTabState = profileTabState,
        onCLick = onClick
      )

    }
  }
}

@Preview(
  name = "Profile Tab Layout",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun ProfileTabLayoutPreview() {
  val profileTabState = rememberProfileTabState()

  ProfileTabLayout(
    profileTabState = profileTabState,
    onClick = {}
  )
}