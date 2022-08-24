package ru.autopulse05.android.feature.user.presentation.sign_up.util

sealed class SignUpUiEvent {
  data class Toast(val text: String): SignUpUiEvent()
  data class TabChange(val value: SignUpTabs): SignUpUiEvent()
  object Success: SignUpUiEvent()
}