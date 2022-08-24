package ru.autopulse05.android.feature.vin.presentation.car.util

import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.feature.vin.presentation.util.VinCarMode

sealed class VinCarEvent {
  object Submit : VinCarEvent()
  data class InitialValuesChange(val parts: List<String>, val car: Car?) : VinCarEvent()
  data class ModeChange(val value: VinCarMode) : VinCarEvent()
  data class VinChange(val value: String) : VinCarEvent()
  data class FrameNumberChange(val value: String) : VinCarEvent()
  data class MarkChange(val value: CarMark) : VinCarEvent()
  data class MarksVisibilityChange(val value: Boolean) : VinCarEvent()
  data class YearsVisibilityChange(val value: Boolean) : VinCarEvent()
  data class YearChange(val value: Int) : VinCarEvent()
  data class ModelsVisibilityChange(val value: Boolean) : VinCarEvent()
  data class ModelChange(val value: CarModel) : VinCarEvent()
  data class ModificationsVisibilityChange(val value: Boolean) : VinCarEvent()
  data class ModificationChange(val value: CarModification) : VinCarEvent()
  data class AdvancedParametersVisibilityChange(val value: Boolean) : VinCarEvent()
}
