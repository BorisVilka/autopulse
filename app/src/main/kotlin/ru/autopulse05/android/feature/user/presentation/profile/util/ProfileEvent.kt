package ru.autopulse05.android.feature.user.presentation.profile.util

sealed class ProfileEvent {
  data class TabChange(val value: ProfileTabs) : ProfileEvent()
  object AddCar : ProfileEvent()
}