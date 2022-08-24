package ru.autopulse05.android.feature.cart.data.remote.dto

import ru.autopulse05.android.feature.cart.domain.model.Position


data class PositionDto(
  val brand: String,
  val number: String,
  val numberFix: String,
  val code: String?,
  val supplierCode: String,
  val description: String,
  val itemKey: String,
  val quantity: Int,
  val comment: String,
  val errorMessage: String? = null,
  val status: Int? = null
)

fun PositionDto.toPosition(): Position = Position(
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