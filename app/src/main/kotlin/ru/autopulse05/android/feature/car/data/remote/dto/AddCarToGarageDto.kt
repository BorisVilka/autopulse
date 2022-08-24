package ru.autopulse05.android.feature.car.data.remote.dto

data class AddCarToGarageDto(
  val comment: String,
  val frame: String,
  val id: Int,
  val mileage: Int,
  val name: String,
  val vehicleRegPlate: String,
  val vin: String,
  val year: Int
)