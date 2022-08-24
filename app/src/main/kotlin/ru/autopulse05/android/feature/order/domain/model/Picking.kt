package ru.autopulse05.android.feature.order.domain.model

data class Picking(
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