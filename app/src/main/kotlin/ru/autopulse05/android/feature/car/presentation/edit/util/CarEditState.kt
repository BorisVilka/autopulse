package ru.autopulse05.android.feature.car.presentation.edit.util

import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import ru.autopulse05.android.shared.presentation.util.FormTextFieldData

data class CarEditState(
  val isLoading: Boolean = false,
  val carId: String? = null,
  val name: FormTextFieldData = FormTextFieldData(value = ""),
  val mode: CarEditMode = CarEditMode.VIN,
  val vin: FormTextFieldData = FormTextFieldData(value = ""),
  val frame: FormTextFieldData = FormTextFieldData(value = ""),
  val year: FormSelectorFieldData<Int> = FormSelectorFieldData(),
  val mark: FormSelectorFieldData<CarMark> = FormSelectorFieldData(),
  val model: FormSelectorFieldData<CarModel> = FormSelectorFieldData(isDisabled = true),
  val modification: FormSelectorFieldData<CarModification> = FormSelectorFieldData(isDisabled = true),
  val regPlate: FormTextFieldData = FormTextFieldData(value = ""),
  val mileage: FormTextFieldData = FormTextFieldData(value = ""),
  val description: FormTextFieldData = FormTextFieldData(value = "")
) {
  val isAlreadyInGarage: Boolean get() = carId != null
}