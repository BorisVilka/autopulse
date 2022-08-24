package ru.autopulse05.android.feature.laximo.presentation.vehicle.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicleFormFieldOption

sealed class LaximoVehicleEvent {
  data class InitialValuesChange(val catalog: String) : LaximoVehicleEvent()
  data class FieldItemsVisibilityChange(val value: Boolean, val index: Int) : LaximoVehicleEvent()
  data class FieldValueChange(
    val value: LaximoVehicleFormFieldOption,
    val index: Int
    ) : LaximoVehicleEvent()
  object Submit : LaximoVehicleEvent()
}