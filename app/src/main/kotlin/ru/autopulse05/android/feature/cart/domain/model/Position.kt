package ru.autopulse05.android.feature.cart.domain.model

import ru.autopulse05.android.feature.cart.data.remote.dto.PositionDto


data class Position(
  val brand: String,
  val number: String,
  val numberFix: String,
  val code: String?,
  val supplierCode: String,
  val description: String,
  val quantity: Int,
  val comment: String,
  val itemKey: String
)

fun Position.toPositionDto() = PositionDto(
  brand = brand,
  number = number,
  numberFix = numberFix,
  code = code,
  supplierCode = supplierCode,
  description = description,
  quantity = quantity,
  comment = comment,
  itemKey = itemKey
)