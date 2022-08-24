package ru.autopulse05.android.feature.order.data.remote.dto


data class OrdersDto(
  val count: Int,
  val items: Map<String,OrderDto>
)


