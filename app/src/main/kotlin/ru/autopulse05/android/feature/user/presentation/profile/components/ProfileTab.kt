package ru.autopulse05.android.feature.user.presentation.profile.components

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabState
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabs
import ru.autopulse05.android.shared.presentation.components.SecondaryButton

@Composable
fun ProfileTab(
  tab: ProfileTabs,
  profileTabState: ProfileTabState,
  modifier: Modifier = Modifier,
  onCLick: (ProfileTabs) -> Unit
) {
  SecondaryButton(
    text = tab.title,
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(
      backgroundColor = if (profileTabState.current == tab) {
        MaterialTheme.colors.background
      } else MaterialTheme.colors.surface
    ),
    onClick = { onCLick(tab) }
  )
}