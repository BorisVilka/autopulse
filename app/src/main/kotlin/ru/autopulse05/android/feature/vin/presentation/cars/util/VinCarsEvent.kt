package ru.autopulse05.android.feature.vin.presentation.cars.util

import ru.autopulse05.android.feature.car.domain.model.Car

sealed class VinCarsEvent {
  data class InitialValuesChange(val parts: List<String>) : VinCarsEvent()
  data class CarClick(val value: Car) : VinCarsEvent()
  object OtherCar : VinCarsEvent()
}
