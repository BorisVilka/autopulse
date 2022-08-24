package ru.autopulse05.android.feature.car.data.remote.dto

import ru.autopulse05.android.feature.car.domain.model.CarMark

data class CarMarkDto(
  val id: Int,
  val name: String
)

fun CarMarkDto.toCarMark() = CarMark(
  id = id,
  name = name
)