package ru.autopulse05.android.feature.vin.presentation.guest.util

import ru.autopulse05.android.feature.vin.domain.model.CarInfo


sealed class VinGuestEvent {
  data class InitialValuesChange(
    val carInfo: CarInfo,
    val parts: List<String>
  ) : VinGuestEvent()

  data class PhoneChange(val value: String) : VinGuestEvent()
  data class NameChange(val value: String) : VinGuestEvent()
  data class EmailChange(val value: String) : VinGuestEvent()
  data class TermsAgreementChange(val value: Boolean) : VinGuestEvent()
  object Submit : VinGuestEvent()
}
