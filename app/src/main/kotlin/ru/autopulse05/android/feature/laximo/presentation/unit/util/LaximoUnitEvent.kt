package ru.autopulse05.android.feature.laximo.presentation.unit.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoDetail


sealed class LaximoUnitEvent {
  data class InitialValuesChange(
    val catalog: String,
    val unitId: String,
    val ssd: String
  ) : LaximoUnitEvent()

  data class DetailClick(val value: LaximoDetail) : LaximoUnitEvent()
}