package ru.autopulse05.android.feature.laximo.presentation.categories.util

sealed class LaximoCategoriesUiEvent {
  data class BasicCategoryClick(
    val value: LaximoCategoryData.Basic,
    val catalog: String,
    val vehicleId: String,
    val ssd: String
  ) : LaximoCategoriesUiEvent()

  data class Toast(val text: String) : LaximoCategoriesUiEvent()
}