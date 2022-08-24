package ru.autopulse05.android.feature.order.data.remote.dto

import ru.autopulse05.android.feature.order.domain.model.Refund


data class RefundDto(
  val id: String,
  val opId: String,
  val status: Int,
  val type: Int,
  val orderPickingGoodId: String,
  val oldItemId: String,
  val itemId: String,
  val quantity: String,
  val attrs: String,
  val product: String,
  val pickingDate: String,
  val comment: String,
  val orderPickingGood: String,
  val orderPicking: String,
  val availableQuantity: String
)

fun RefundDto.toRefund() = Refund(
  id = id,
  opId = opId,
  status = status,
  type = type,
  orderPickingGoodId = orderPickingGoodId,
  oldItemId = oldItemId,
  itemId = itemId,
  quantity = quantity,
  attrs = attrs,
  product = product,
  pickingDate = pickingDate,
  comment = comment,
  orderPickingGood = orderPickingGood,
  orderPicking = orderPicking,
  availableQuantity = availableQuantity
)



