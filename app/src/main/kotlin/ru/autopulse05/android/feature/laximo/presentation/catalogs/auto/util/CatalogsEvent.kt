package ru.autopulse05.android.feature.laximo.presentation.catalogs.auto.util

import ru.autopulse05.android.feature.laximo.domain.model.LaximoCatalog

sealed class CatalogsEvent {
  data class VinChange(val value: String) : CatalogsEvent()
  data class CarcaseCodeChange(val value: String) : CatalogsEvent()
  data class CarcaseNumberChange(val value: String) : CatalogsEvent()
  object SearchByVin : CatalogsEvent()
  object SearchByFrame : CatalogsEvent()
  data class OnCatalogClick(val value: LaximoCatalog) : CatalogsEvent()
}