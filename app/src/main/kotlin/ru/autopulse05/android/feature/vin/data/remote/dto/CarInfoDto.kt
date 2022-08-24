package ru.autopulse05.android.feature.vin.data.remote.dto

import ru.autopulse05.android.feature.vin.domain.model.CarInfo

data class CarInfoDto(
  val brand: String,
  val model: String? = null,
  val modificationId: Int? = null,
  val modification: String? = null,
  val vin: String,
  val frame: String,
  val hp: String? = null,
  val year: Int? = null,
)

fun CarInfoDto.toCarInfo() = CarInfo(
  brand = brand,
  model = model,
  modificationId = modificationId,
  modification = modification,
  vin = vin,
  frame = frame,
  hp = hp,
  year = year
)