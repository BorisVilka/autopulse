package ru.autopulse05.android.feature.user.presentation.profile.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabState
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabs
import ru.autopulse05.android.feature.user.presentation.profile.util.rememberProfileTabState

@Composable
fun ProfileTabPage(
  tabs: List<ProfileTabs>,
  profileTabState: ProfileTabState
) {

  if (profileTabState.current != null) {
    tabs.first { it.index == profileTabState.current!!.index }.draw()
  }
}

@Preview(
  name = "Profile Tab Page",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun ProfileTabPagePreview() {
  val tabs = listOf(
    ProfileTabs.Orders,
    ProfileTabs.Pickings,
    ProfileTabs.Refunds,
    ProfileTabs.Garage,
    ProfileTabs.Data,
  )
  val profileTabState = rememberProfileTabState()

  ProfileTabPage(
    tabs = tabs,
    profileTabState = profileTabState
  )
}