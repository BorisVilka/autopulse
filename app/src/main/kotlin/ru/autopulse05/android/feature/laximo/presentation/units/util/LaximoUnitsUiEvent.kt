package ru.autopulse05.android.feature.laximo.presentation.units.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoUnit

sealed class LaximoUnitsUiEvent {
  data class OnUnitClicked(val value: LaximoUnit, val catalog: String) : LaximoUnitsUiEvent()
  data class Toast(val text: String) : LaximoUnitsUiEvent()
}