package ru.autopulse05.android.feature.laximo.presentation.catalogs.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoCatalog
import ru.autopulse05.android.shared.presentation.util.FormTextFieldData

data class CatalogsState(
  val items: List<LaximoCatalog> = listOf(),
  val vin: FormTextFieldData = FormTextFieldData(),
  val isLoading: Boolean = true,
  val frameName: FormTextFieldData = FormTextFieldData(),
  val frameNumber: FormTextFieldData = FormTextFieldData(),
)
