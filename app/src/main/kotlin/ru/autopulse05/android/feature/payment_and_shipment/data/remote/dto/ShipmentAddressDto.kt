package ru.autopulse05.android.feature.payment_and_shipment.data.remote.dto

import ru.autopulse05.android.feature.payment_and_shipment.domain.model.ShipmentAddress

data class ShipmentAddressDto(
  val id: String,
  val name: String
)

fun ShipmentAddressDto.toShipmentAddress() = ShipmentAddress(
  id = id,
  name = name
)