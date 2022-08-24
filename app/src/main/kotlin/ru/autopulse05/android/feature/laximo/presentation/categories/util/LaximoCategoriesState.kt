package ru.autopulse05.android.feature.laximo.presentation.categories.util

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LaximoCategoriesState(
  val catalog: String = "",
  val vehicleId: String = "",
  val ssd: String = "",
  val isLoading: Boolean = true,
  val categories: PersistentList<LaximoCategoryData> = persistentListOf(),
)
