package ru.autopulse05.android.feature.user.presentation.not_signed_in.util

sealed class NotSignedInEvent {
  object SignUp : NotSignedInEvent()
  object SignIn: NotSignedInEvent()
}