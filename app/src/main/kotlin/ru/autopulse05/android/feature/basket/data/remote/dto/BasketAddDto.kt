package ru.autopulse05.android.feature.basket.data.remote.dto


data class BasketAddDto(
  val status: Int,
  val positions: List<PositionDto>,
)