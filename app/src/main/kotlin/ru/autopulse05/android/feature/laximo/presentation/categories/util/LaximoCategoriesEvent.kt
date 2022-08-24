package ru.autopulse05.android.feature.laximo.presentation.categories.util


sealed class LaximoCategoriesEvent {
  data class InitialValuesChange(
    val catalog: String,
    val vehicleId: String,
    val ssd: String,
  ) : LaximoCategoriesEvent()

  data class ExpandableCategoryClick(
    val value: LaximoCategoryData.Expandable
  ) : LaximoCategoriesEvent()

  data class BasicCategoryClick(
    val value: LaximoCategoryData.Basic
  ) : LaximoCategoriesEvent()
}