package ru.autopulse05.android.feature.order.data.remote.dto

import ru.autopulse05.android.feature.cart.data.remote.dto.PositionDto


data class CreateOrderDto(
  val number: Int,
  val status: String,
  val statusId: String,
  val statusCode: String,
  val positionsQuantity: Int,
  val sum: String,
  val date: String,
  val comment: String,
  val wholeOrderOnly: String,
  val positions: List<PositionDto>
)