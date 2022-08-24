package ru.autopulse05.android.feature.car.data.remote.dto

import ru.autopulse05.android.feature.car.domain.model.CarModel

data class CarModelDto(
  val id: Int,
  val name: String,
  val yearFrom: String,
  val yearTo: String?
)

fun CarModelDto.toCarModel() = CarModel(
  id = id,
  name = name,
  yearFrom = yearFrom,
  yearTo = yearTo
)