package ru.autopulse05.android.feature.user.presentation.not_signed_in.util

sealed class NotSignedInUiEvent {
  object SignUp : NotSignedInUiEvent()
  object SignIn: NotSignedInUiEvent()
}