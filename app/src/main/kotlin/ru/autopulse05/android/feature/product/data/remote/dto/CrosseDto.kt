package ru.autopulse05.android.feature.product.data.remote.dto

import ru.autopulse05.android.feature.product.domain.model.Crosse

data class CrosseDto(
  val brand: String,
  val crossType: Int,
  val number: String,
  val numberFix: String
)

fun CrosseDto.toCrosse() =
  Crosse(
    brand = brand,
    number = number
  )