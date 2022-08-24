package ru.autopulse05.android.feature.garage.presentation.add.util

import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification

sealed class AddToGarageEvent {
  data class TitleChanged(val value: String) : AddToGarageEvent()
  data class ModeChanged(val value: AddToGarageMode) : AddToGarageEvent()
  data class VinCodeChanged(val value: String) : AddToGarageEvent()
  data class CascadeNumberChanged(val value: String) : AddToGarageEvent()
  data class YearsVisibilityChanged(val value: Boolean) : AddToGarageEvent()
  data class YearChanged(val value: Int) : AddToGarageEvent()
  data class MarksVisibilityChanged(val value: Boolean) : AddToGarageEvent()
  data class MarkChanged(val value: CarMark) : AddToGarageEvent()
  data class ModelsVisibilityChanged(val value: Boolean) : AddToGarageEvent()
  data class ModelChanged(val value: CarModel) : AddToGarageEvent()
  data class ModificationsVisibilityChanged(val value: Boolean) : AddToGarageEvent()
  data class ModificationChanged(val value: CarModification) : AddToGarageEvent()
  data class CountryNumberChanged(val value: String) : AddToGarageEvent()
  data class MileageChanged(val value: String) : AddToGarageEvent()
  data class DescriptionChanged(val value: String) : AddToGarageEvent()
  object Submit : AddToGarageEvent()
}