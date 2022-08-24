package ru.autopulse05.android.feature.laximo.data.remote.dto

import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicleFormField

data class LaximoVehicleFormFieldDto(
  val allowListVehicles: String,
  val name: String,
  val determined: String,
  val value: String?,
  val ssd: String?,
  val type: String?,
  val automatic: String,
  val options: List<LaximoVehicleFormFieldOptionDto>
)

fun LaximoVehicleFormFieldDto.toLaximoVehicleFormField() = LaximoVehicleFormField(
  allowListVehicles = allowListVehicles,
  name = name,
  determined = determined,
  value = value,
  ssd = ssd,
  type = type,
  automatic = automatic,
  options = options.map { it.toLaximoVehicleFormFieldOption() },
)
