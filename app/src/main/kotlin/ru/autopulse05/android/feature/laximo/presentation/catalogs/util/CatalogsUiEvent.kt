package ru.autopulse05.android.feature.laximo.presentation.catalogs.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoCatalog
import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle

sealed class CatalogsUiEvent {
  data class Toast(val text: String) : CatalogsUiEvent()
  data class GoToVehicles(val vehicles: List<LaximoVehicle>) : CatalogsUiEvent()
  data class GoToVehicleSearch(val value: LaximoCatalog) : CatalogsUiEvent()
}