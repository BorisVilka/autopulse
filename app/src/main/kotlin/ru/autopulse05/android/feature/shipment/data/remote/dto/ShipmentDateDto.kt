package ru.autopulse05.android.feature.shipment.data.remote.dto

import ru.autopulse05.android.feature.shipment.domain.model.ShipmentDate

data class ShipmentDateDto(
  val date: String,
  val name: String
)

fun ShipmentDateDto.toShipmentDate() = ShipmentDate(
  date = date,
  name = name
)