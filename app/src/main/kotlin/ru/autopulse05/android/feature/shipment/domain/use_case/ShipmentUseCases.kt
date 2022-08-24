package ru.autopulse05.android.feature.shipment.domain.use_case


data class ShipmentUseCases(

  val getShipmentMethods: ShipmentGetMethodsUseCase,
  val getShipmentOffices: ShipmentGetOfficesUseCase,
  val getShipmentAddresses: ShipmentUpdateAddressesUseCase,
  val getShipmentDates: ShipmentGetDatesUseCase,
  val addShipmentAddress: ShipmentAddAddressUseCase
)