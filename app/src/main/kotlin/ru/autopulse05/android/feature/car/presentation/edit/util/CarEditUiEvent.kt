package ru.autopulse05.android.feature.car.presentation.edit.util

sealed class CarEditUiEvent {
  data class Toast(val text: String) : CarEditUiEvent()
  object NotLoggedIn: CarEditUiEvent()
  object Success : CarEditUiEvent()
}