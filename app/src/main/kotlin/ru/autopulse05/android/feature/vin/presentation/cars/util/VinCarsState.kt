package ru.autopulse05.android.feature.vin.presentation.cars.util

import ru.autopulse05.android.feature.car.domain.model.Car

data class VinCarsState(
  val isLoading: Boolean = true,
  val parts: List<String> = listOf(),
  val cars: List<Car> = listOf()
)
