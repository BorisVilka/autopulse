package ru.autopulse05.android.feature.order.data.remote.dto

import ru.autopulse05.android.feature.order.domain.model.CreateComplaintPosition

data class CreateComplaintPositionDto(
  val id: String,
  val opId: String,
  val status: Int,
  val type: Int,
  val orderPickingGoodId: String,
  val oldItemId: String,
  val itemId: String,
  val locId: String,
  val quantity: Int,
  val attrs: String,
  val pickingDate: String,
  val comment: String,
  val product: String,
  val orderPickingGood: String,
  val orderPicking: String,
  val availableQuantity: Int
)

fun CreateComplaintPositionDto.toCreateComplaintPosition() = CreateComplaintPosition(
  id = id,
  opId = opId,
  status = status,
  type = type,
  orderPickingGoodId = orderPickingGoodId,
  oldItemId = oldItemId,
  itemId = itemId,
  locId = locId,
  quantity = quantity,
  attrs = attrs,
  pickingDate = pickingDate,
  comment = comment,
  product = product,
  orderPickingGood = orderPickingGood,
  orderPicking = orderPicking,
  availableQuantity = availableQuantity,
)