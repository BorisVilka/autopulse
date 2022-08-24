package ru.autopulse05.android.feature.garage.data.remote.dto

data class AddCarDto(
  val comment: String,
  val frame: String,
  val id: Int,
  val mileage: Int,
  val name: String,
  val vehicleRegPlate: String,
  val vin: String,
  val year: Int
)