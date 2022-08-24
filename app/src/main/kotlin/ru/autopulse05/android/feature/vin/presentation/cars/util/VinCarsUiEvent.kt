package ru.autopulse05.android.feature.vin.presentation.cars.util

import ru.autopulse05.android.feature.car.domain.model.Car

sealed class VinCarsUiEvent {
  data class Toast(val text: String): VinCarsUiEvent()
  data class SelectCar(val parts: List<String>, val car: Car) : VinCarsUiEvent()
  data class OtherCar(val parts: List<String>) : VinCarsUiEvent()
}
