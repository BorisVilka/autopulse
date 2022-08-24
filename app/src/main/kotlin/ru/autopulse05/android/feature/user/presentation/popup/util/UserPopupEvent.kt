package ru.autopulse05.android.feature.user.presentation.popup.util

sealed class UserPopupEvent {
  data class ItemClick(val value: UserPopupLinks): UserPopupEvent()
  object SignOut: UserPopupEvent()
  object Close: UserPopupEvent()
}
