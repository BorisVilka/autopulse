package ru.autopulse05.android.feature.laximo.presentation.categories.util

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import ru.autopulse05.android.feature.laximo.data.remote.dto.LaximoQuickGroupDto

data class LaximoCategoriesState(
  val catalog: String = "",
  val vehicleId: String = "",
  val ssd: String = "",
  val isLoading: Boolean = true,
  val categories: PersistentList<LaximoCategoryData> = persistentListOf(),
  val quickGroup: List<LaximoQuickGroupDto> = mutableListOf(),
  val vis: PersistentMap<String,Boolean> = persistentMapOf()
)
