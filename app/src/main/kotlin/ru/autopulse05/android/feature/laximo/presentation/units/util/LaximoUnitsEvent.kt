package ru.autopulse05.android.feature.laximo.presentation.units.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoUnit


sealed class LaximoUnitsEvent {
  data class OnInitialValuesChanged(
    val catalog: String,
    val categoryId: String,
    val ssd: String,
    val vehicleId: String
  ) : LaximoUnitsEvent()

  data class OnUnitClicked(val value: LaximoUnit) : LaximoUnitsEvent()
}