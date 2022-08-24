package ru.autopulse05.android.feature.laximo.presentation.vehicles.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle

data class LaximoVehiclesState(
  val vehicles: List<LaximoVehicle> = listOf()
)
