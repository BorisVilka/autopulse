package ru.autopulse05.android.feature.car.domain.model

data class CarModel(
  val id: Int,
  val name: String,
  val yearFrom: String,
  val yearTo: String?
)