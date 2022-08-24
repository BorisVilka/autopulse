package ru.autopulse05.android.feature.order.data.remote.dto


data class TsOrdersDto(
  val total: Int,
  val list: List<TsOrderDto>,
)

