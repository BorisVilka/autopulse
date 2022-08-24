package ru.autopulse05.android.feature.laximo.presentation.vehicle.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle

sealed class LaximoVehicleUiEvent {
  data class Toast(val text: String) : LaximoVehicleUiEvent()
  data class GoToVehicles(val vehicles: List<LaximoVehicle>) : LaximoVehicleUiEvent()
}