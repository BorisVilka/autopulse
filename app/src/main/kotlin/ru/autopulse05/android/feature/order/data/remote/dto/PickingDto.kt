package ru.autopulse05.android.feature.order.data.remote.dto

import ru.autopulse05.android.feature.order.domain.model.Picking


data class PickingDto(
  val id: String,
  val opId: String,
  val coPositionId: String,
  val oldCoPositionId: String,
  val quantity: String,
  val itemId: String,
  val clToResRate: String,
  val sellPrice: String,
  val clSellPrice: String,
  val product: String,
  val operationInfo: String,
  val availableQuantityCC: String
)

fun PickingDto.toPicking() = Picking(
  id = id,
  opId = opId,
  coPositionId = coPositionId,
  oldCoPositionId = oldCoPositionId,
  quantity = quantity,
  itemId = itemId,
  clToResRate = clToResRate,
  sellPrice = sellPrice,
  product = product,
  operationInfo = operationInfo,
  availableQuantityCC = availableQuantityCC,
  clSellPrice = clSellPrice
)



