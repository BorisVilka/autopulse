package ru.autopulse05.android.feature.laximo.presentation.unit.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoDetail

sealed class LaximoUnitUiEvent {
  data class Toast(val text: String) : LaximoUnitUiEvent()
  data class OnDetailClicked(val value: LaximoDetail) : LaximoUnitUiEvent()
}