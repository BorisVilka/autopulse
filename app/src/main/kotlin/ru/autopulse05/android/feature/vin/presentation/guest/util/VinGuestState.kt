package ru.autopulse05.android.feature.vin.presentation.guest.util

import ru.autopulse05.android.feature.vin.domain.model.CarInfo
import ru.autopulse05.android.shared.presentation.util.FormTextFieldData

data class VinGuestState(
  val isLoading: Boolean = false,
  val carInfo: CarInfo? = null,
  val parts: List<String> = listOf(),
  val name: FormTextFieldData = FormTextFieldData(),
  val phone: FormTextFieldData = FormTextFieldData(),
  val email: FormTextFieldData = FormTextFieldData(),
  val termsAgreement: Boolean = true
)
