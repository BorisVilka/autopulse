package ru.autopulse05.android.feature.user.presentation.profile.util

import ru.autopulse05.android.feature.car.domain.model.Car


sealed class ProfileUiEvent {
  data class TabChange(val value: ProfileTabs) : ProfileUiEvent()
  data class EditCar(val value: Car) : ProfileUiEvent()
  object AddCar : ProfileUiEvent()
  object SignOut : ProfileUiEvent()
  object NotSignedIn : ProfileUiEvent()
  data class Toast(val text: String): ProfileUiEvent()
}