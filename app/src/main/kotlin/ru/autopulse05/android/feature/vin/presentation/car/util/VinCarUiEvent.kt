package ru.autopulse05.android.feature.vin.presentation.car.util

import ru.autopulse05.android.feature.vin.domain.model.CarInfo

sealed class VinCarUiEvent {
  data class Toast(val text: String) : VinCarUiEvent()
  data class GoToGuest(val parts: List<String>, val carInfo: CarInfo) : VinCarUiEvent()
  object GoToStore : VinCarUiEvent()
}
