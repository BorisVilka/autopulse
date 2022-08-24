package ru.autopulse05.android.feature.cart.data.remote.dto


data class CartAddDto(
  val status: Int,
  val positions: List<PositionDto>,
)