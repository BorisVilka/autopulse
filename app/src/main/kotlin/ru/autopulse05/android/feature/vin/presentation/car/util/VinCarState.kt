package ru.autopulse05.android.feature.vin.presentation.car.util

import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.feature.vin.presentation.util.VinCarMode
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import ru.autopulse05.android.shared.presentation.util.FormTextFieldData

data class VinCarState(
  val isLoading: Boolean = false,
  val mode: VinCarMode = VinCarMode.VIN,
  val vin: FormTextFieldData = FormTextFieldData(),
  val frameNumber: FormTextFieldData = FormTextFieldData(value = ""),
  val mark: FormSelectorFieldData<CarMark> = FormSelectorFieldData(),
  val year: FormSelectorFieldData<Int> = FormSelectorFieldData(),
  val model: FormSelectorFieldData<CarModel> = FormSelectorFieldData(isDisabled = true),
  val modification: FormSelectorFieldData<CarModification> = FormSelectorFieldData(isDisabled = true),
  val parts: List<String> = listOf(),
  val isAdvancedParametersVisible: Boolean = false
)
