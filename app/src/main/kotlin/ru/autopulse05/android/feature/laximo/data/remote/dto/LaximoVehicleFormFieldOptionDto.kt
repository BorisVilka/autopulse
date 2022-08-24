package ru.autopulse05.android.feature.laximo.data.remote.dto

import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicleFormFieldOption

data class LaximoVehicleFormFieldOptionDto(
  val key: String,
  val value: String
)

fun LaximoVehicleFormFieldOptionDto.toLaximoVehicleFormFieldOption() = LaximoVehicleFormFieldOption(
  key = key,
  value = value
)