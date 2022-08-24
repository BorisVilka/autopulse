package ru.autopulse05.android.feature.garage.data.remote.dto

import ru.autopulse05.android.feature.garage.domain.model.Car

data class CarDto(
  val id: String,
  val name: String,
  val comment: String,
  val year: Int,
  val vin: String,
  val frame: String,
  val mileage: String,
  val manufacturerId: Int,
  val manufacturer: String,
  val modelId: Int,
  val model: String,
  val modificationId: Int,
  val vehicleRegPlate: String
)

fun CarDto.toCar() = Car(
  id = id,
  name = name,
  comment = comment,
  year = year,
  vin = vin,
  frame = frame,
  mileage = mileage,
  manufacturerId = manufacturerId,
  manufacturer = manufacturer,
  modelId = modelId,
  model = model,
  modificationId = modificationId,
  vehicleRegPlate = vehicleRegPlate
)