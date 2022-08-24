package ru.autopulse05.android.feature.shipment.data.remote.dto

import ru.autopulse05.android.feature.shipment.domain.model.ShipmentMethod

data class ShipmentMethodDto(
  val id: String,
  val name: String
)

fun ShipmentMethodDto.toShipmentMethod() = ShipmentMethod(
  id = id,
  name = name
)