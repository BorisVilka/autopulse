package ru.autopulse05.android.feature.vin.presentation.parts.util

sealed class VinPartsUiEvent {
  data class GoToCar(val parts: List<String>) : VinPartsUiEvent()
  data class GoToCars(val parts: List<String>) : VinPartsUiEvent()
}
