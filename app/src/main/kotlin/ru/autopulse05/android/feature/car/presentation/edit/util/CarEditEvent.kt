package ru.autopulse05.android.feature.car.presentation.edit.util

import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification

sealed class CarEditEvent {
  data class InitialValuesChange(val car: Car?) : CarEditEvent()
  data class TitleChange(val value: String) : CarEditEvent()
  data class ModeChange(val value: CarEditMode) : CarEditEvent()
  data class VinCodeChange(val value: String) : CarEditEvent()
  data class CascadeNumberChange(val value: String) : CarEditEvent()
  data class YearsVisibilityChange(val value: Boolean) : CarEditEvent()
  data class YearChange(val value: Int) : CarEditEvent()
  data class MarksVisibilityChange(val value: Boolean) : CarEditEvent()
  data class MarkChange(val value: CarMark) : CarEditEvent()
  data class ModelsVisibilityChange(val value: Boolean) : CarEditEvent()
  data class ModelChange(val value: CarModel) : CarEditEvent()
  data class ModificationsVisibilityChange(val value: Boolean) : CarEditEvent()
  data class ModificationChange(val value: CarModification) : CarEditEvent()
  data class CountryNumberChange(val value: String) : CarEditEvent()
  data class MileageChange(val value: String) : CarEditEvent()
  data class DescriptionChange(val value: String) : CarEditEvent()
  object Submit : CarEditEvent()
}