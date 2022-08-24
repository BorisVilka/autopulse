package ru.autopulse05.android.feature.order.domain.model

data class CreateComplaintPosition(
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