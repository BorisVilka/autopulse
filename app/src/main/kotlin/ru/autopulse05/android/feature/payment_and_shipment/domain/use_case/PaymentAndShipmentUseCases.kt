package ru.autopulse05.android.feature.payment_and_shipment.domain.use_case

data class PaymentAndShipmentUseCases(

  // Payment
  val getPaymentMethods: PaymentGetMethodsUseCase,

  // Shipment
  val getShipmentMethods: ShipmentGetMethodsUseCase,
  val getShipmentOffices: ShipmentGetOfficesUseCase,
  val getShipmentAddresses: ShipmentUpdateAddressesUseCase,
  val getShipmentDates: ShipmentGetDatesUseCase,
  val addShipmentAddress: ShipmentAddAddressUseCase
)