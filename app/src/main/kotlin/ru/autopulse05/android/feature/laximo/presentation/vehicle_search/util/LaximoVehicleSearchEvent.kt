package ru.autopulse05.android.feature.laximo.presentation.vehicle_search.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicleFormFieldOption

sealed class LaximoVehicleSearchEvent {
  data class OnInitialValuesChange(val catalog: String) : LaximoVehicleSearchEvent()
  data class OnFieldItemsVisibilityChange(
    val value: Boolean,
    val index: Int
  ) : LaximoVehicleSearchEvent()

  data class OnFieldValueChange(
    val value: LaximoVehicleFormFieldOption,
    val index: Int
  ) : LaximoVehicleSearchEvent()

  object Search : LaximoVehicleSearchEvent()
}