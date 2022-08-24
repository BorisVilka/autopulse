package ru.autopulse05.android.feature.user.presentation.sign_in.util


sealed class SignInEvent {
  object ForgotPassword : SignInEvent()
  object NotSignedUp : SignInEvent()
  data class LoginChange(val value: String) : SignInEvent()
  data class PasswordChange(val value: String) : SignInEvent()
  object Submit : SignInEvent()
}