package ru.autopulse05.android.feature.laximo.presentation.vehicle_search.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle

sealed class LaximoVehicleSearchUiEvent {
  data class Toast(val text: String) : LaximoVehicleSearchUiEvent()
  data class GoToVehicles(val vehicles: List<LaximoVehicle>) : LaximoVehicleSearchUiEvent()
}