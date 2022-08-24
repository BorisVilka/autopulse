package ru.autopulse05.android.feature.car.data.remote.dto

import ru.autopulse05.android.feature.car.domain.model.CarModification


data class CarModificationDto(
  val constructionType: String,
  val cylinderCapacityCcm: Int,
  val fuelType: String,
  val fullName: String,
  val id: Int,
  val manufacturer: String,
  val manufacturerId: Int,
  val model: String,
  val modelId: Int,
  val motorCodes: String,
  val name: String,
  val powerHP: Int,
  val powerKW: Int,
  val yearFrom: String,
  val yearTo: String?
)

fun CarModificationDto.toCarModification() = CarModification(
  constructionType = constructionType,
  cylinderCapacityCcm = cylinderCapacityCcm,
  fuelType = fuelType,
  fullName = fullName,
  id = id,
  manufacturer = manufacturer,
  manufacturerId = manufacturerId,
  model = model,
  modelId = modelId,
  motorCodes = motorCodes,
  name = name,
  powerHP = powerHP,
  powerKW = powerKW,
  yearFrom = yearFrom,
  yearTo = yearTo
)