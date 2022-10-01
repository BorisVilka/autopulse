package ru.autopulse05.android.feature.order.data.remote.dto

import ru.autopulse05.android.feature.cart.data.remote.dto.PositionDto
import ru.autopulse05.android.feature.cart.data.remote.dto.toPosition
import ru.autopulse05.android.feature.order.domain.model.Order


data class OrderDto(
  val number: String?,
  val status: String?,
  val statusCode: String?,
  val statusId: String,
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
  val positions: List<PositionDto>?
)

fun OrderDto.toOrder() = Order(
  number = number,
  positionsQuantity = positionsQuantity,
  deliveryAddressId = deliveryAddressId,
  deliveryAddress = deliveryAddress,
  deliveryOfficeId = deliveryOfficeId,
  deliveryOffice = deliveryOffice,
  deliveryTypeId = deliveryTypeId,
    deliveryType = deliveryType,
    paymentTypeId = paymentTypeId,
    paymentType = paymentType,
    deliveryCost = deliveryCost,
    shipmentDate = shipmentDate,
    sum = sum,
    date = date,
    debt = debt,
    comment = comment,
    clientOrderNumber = clientOrderNumber,
    positions = positions!!.map { it.toPosition() },
    status = status.orEmpty(),
  statusId =  statusId
)