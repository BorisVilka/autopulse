package ru.autopulse05.android.feature.user.presentation.sign_in.util

sealed class SignInUiEvent {
  object Success : SignInUiEvent()
  object SignUp : SignInUiEvent()
  object ForgotPassword : SignInUiEvent()
}