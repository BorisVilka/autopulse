package ru.autopulse05.android.feature.garage.presentation.add.util

import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import ru.autopulse05.android.shared.presentation.util.FormTextFieldData

data class AddToGarageFormState(
  val name: FormTextFieldData = FormTextFieldData(value = ""),
  val mode: AddToGarageMode = AddToGarageMode.VIN,
  val vinCode: FormTextFieldData = FormTextFieldData(value = ""),
  val frame: FormTextFieldData = FormTextFieldData(value = ""),
  val year: FormSelectorFieldData<Int> = FormSelectorFieldData(),
  val mark: FormSelectorFieldData<CarMark> = FormSelectorFieldData(),
  val model: FormSelectorFieldData<CarModel> = FormSelectorFieldData(isDisabled = true),
  val modification: FormSelectorFieldData<CarModification> = FormSelectorFieldData(isDisabled = true),
  val regPlate: FormTextFieldData = FormTextFieldData(value = ""),
  val mileage: FormTextFieldData = FormTextFieldData(value = ""),
  val description: FormTextFieldData = FormTextFieldData(value = "")
)