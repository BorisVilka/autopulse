package ru.autopulse05.android.feature.user.presentation.new_password.util

sealed class NewPasswordEvent {
  data class PhoneChanged(val value: String) : NewPasswordEvent()
  data class CodeChanged(val value: String) : NewPasswordEvent()
  data class NewPasswordChanged(val value: String) : NewPasswordEvent()
  object Submit : NewPasswordEvent()
}