package ru.autopulse05.android.feature.order.data.remote.dto


data class CreateOrdersDto(
  val status: Int,
  val errorMessage: String,
  val clientOrderNumber: String,
  val orders: Map<String,CreateOrderDto>
)