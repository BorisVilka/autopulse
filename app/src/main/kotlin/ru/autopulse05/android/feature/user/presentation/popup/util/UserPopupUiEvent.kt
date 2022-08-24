package ru.autopulse05.android.feature.user.presentation.popup.util

sealed class UserPopupUiEvent {
  data class Toast(val text: String): UserPopupUiEvent()
  data class ItemClick(val value: UserPopupLinks): UserPopupUiEvent()
  object Close: UserPopupUiEvent()
}
