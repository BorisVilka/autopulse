package ru.autopulse05.android.feature.laximo.presentation.vehicles.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle

sealed class LaximoVehiclesUiEvent {
  data class GoToCategories(val value: LaximoVehicle) : LaximoVehiclesUiEvent()
}