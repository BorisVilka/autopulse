package ru.autopulse05.android.feature.shipment.data.remote.dto

import ru.autopulse05.android.feature.shipment.domain.model.ShipmentAddress

data class ShipmentAddressDto(
  val id: String,
  val name: String
)

fun ShipmentAddressDto.toShipmentAddress() = ShipmentAddress(
  id = id,
  name = name
)