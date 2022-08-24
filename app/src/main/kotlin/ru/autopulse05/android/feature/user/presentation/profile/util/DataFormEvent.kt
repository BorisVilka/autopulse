package ru.autopulse05.android.feature.user.presentation.profile.util

sealed class DataFormEvent {
  data class NewPasswordChanged(val value: String) : DataFormEvent()
  object Submit : DataFormEvent()
}