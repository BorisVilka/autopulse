package ru.autopulse05.android.feature.garage.presentation.add.util

sealed class AddToGarageUiEvent {
  object NotLoggedIn: AddToGarageUiEvent()
  object Success : AddToGarageUiEvent()
}