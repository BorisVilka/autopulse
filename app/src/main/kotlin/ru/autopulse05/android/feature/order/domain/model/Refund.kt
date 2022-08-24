package ru.autopulse05.android.feature.order.domain.model

data class Refund(
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