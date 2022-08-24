package ru.autopulse05.android.feature.user.presentation.profile.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import ru.autopulse05.android.shared.data.ext.fromJson
import ru.autopulse05.android.shared.data.ext.toJson

@Composable
fun rememberProfileTabState(
  initialValue: ProfileTabs? = null,
  confirmStateChange: (ProfileTabs) -> Boolean = { true }
) = rememberSaveable(saver = ProfileTabState.Saver(confirmStateChange)) {
  ProfileTabState(initialValue, confirmStateChange)
}

class ProfileTabState(
  initialValue: ProfileTabs? = null,
  private val confirmStateChange: (ProfileTabs) -> Boolean = { true }
) {
  private var currentValue by mutableStateOf(initialValue)

  val current: ProfileTabs? get() = currentValue

  private fun set(targetValue: ProfileTabs) {
    currentValue = targetValue
  }

  fun change(value: ProfileTabs) {
    if (confirmStateChange(value)) set(value)
  }

  companion object {
    fun Saver(confirmStateChange: (ProfileTabs) -> Boolean) =
      androidx.compose.runtime.saveable.Saver<ProfileTabState, String>(
        save = { it.currentValue.toJson() },
        restore = { ProfileTabState(it.fromJson(ProfileTabs::class.java), confirmStateChange) }
      )
  }
}