package ru.autopulse05.android.feature.laximo.domain.model

data class LaximoVehicleFormField(
  val allowListVehicles: String,
  val name: String,
  val determined: String,
  val value: String?,
  val ssd: String?,
  val type: String?,
  val automatic: String,
  val options: List<LaximoVehicleFormFieldOption>
)