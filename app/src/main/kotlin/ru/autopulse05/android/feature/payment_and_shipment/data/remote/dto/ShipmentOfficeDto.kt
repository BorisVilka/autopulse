package ru.autopulse05.android.feature.payment_and_shipment.data.remote.dto

import ru.autopulse05.android.feature.payment_and_shipment.domain.model.ShipmentOffice


data class ShipmentOfficeDto(
  val id: String,
  val name: String
)

fun ShipmentOfficeDto.toShipmentOffice() = ShipmentOffice(
  id = id,
  name = name
)