package ru.autopulse05.android.feature.laximo.presentation.unit.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoDetail
import ru.autopulse05.android.feature.laximo.domain.model.LaximoUnit

data class LaximoUnitState(
  val catalog: String = "",
  val isLoading: Boolean = true,
  val unit: LaximoUnit? = null,
  val details: List<LaximoDetail> = listOf()
)
