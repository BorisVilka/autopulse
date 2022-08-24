package ru.autopulse05.android.feature.order.domain.model

import ru.autopulse05.android.feature.cart.domain.model.Position


data class Order(
  val number: String?,
  val positionsQuantity: String?,
  val deliveryAddressId: String?,
  val deliveryAddress: String?,
  val deliveryOfficeId: String?,
  val deliveryOffice: String?,
  val deliveryTypeId: String?,
  val deliveryType: String?,
  val paymentTypeId: String?,
  val paymentType: String?,
  val deliveryCost: String?,
  val shipmentDate: String?,
  val sum: String?,
  val date: String?,
  val debt: String?,
  val comment: String?,
  val clientOrderNumber: String?,
  val positions: List<Position>?
)