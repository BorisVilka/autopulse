package ru.autopulse05.android.feature.payment_and_shipment.data.remote.dto

import ru.autopulse05.android.feature.payment_and_shipment.domain.model.ShipmentDate

data class ShipmentDateDto(
  val date: String,
  val name: String
)

fun ShipmentDateDto.toShipmentDate() = ShipmentDate(
  date = date,
  name = name
)