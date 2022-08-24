package ru.autopulse05.android.feature.laximo.presentation.units.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoUnit

data class LaximoUnitsState(
  val catalog: String = "",
  val isLoading: Boolean = true,
  val ssd: String = "",
  val vehicleId: String = "",
  val categoryId: String = "",
  val units: List<LaximoUnit> = listOf()
)
