package ru.autopulse05.android.feature.vin.presentation.guest.util

sealed class VinGuestUiEvent {
  data class Toast(val text: String) : VinGuestUiEvent()
  object GoToStore : VinGuestUiEvent()
}
