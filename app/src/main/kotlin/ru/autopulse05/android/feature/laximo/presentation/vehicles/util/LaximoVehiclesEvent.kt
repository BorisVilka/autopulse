package ru.autopulse05.android.feature.laximo.presentation.vehicles.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle


sealed class LaximoVehiclesEvent {
  data class OnInitialValuesChanged(
    val vehicles: List<LaximoVehicle>
    ) : LaximoVehiclesEvent()
  data class OnVehicleClicked(val value: LaximoVehicle) : LaximoVehiclesEvent()
}