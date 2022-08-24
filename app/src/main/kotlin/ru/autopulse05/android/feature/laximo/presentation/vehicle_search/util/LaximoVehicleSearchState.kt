package ru.autopulse05.android.feature.laximo.presentation.vehicle_search.util

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicleFormFieldOption
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData

data class LaximoVehicleSearchState(
  val catalog: String = "",
  val fields: PersistentList<Pair<String, FormSelectorFieldData<LaximoVehicleFormFieldOption>>> = persistentListOf(),
  val ssd: String = "",
  val isLoading: Boolean = true
)
