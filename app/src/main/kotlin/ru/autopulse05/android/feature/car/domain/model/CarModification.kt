package ru.autopulse05.android.feature.car.domain.model


data class CarModification(
  val constructionType: String,
  val cylinderCapacityCcm: Int,
  val fuelType: String,
  val fullName: String,
  val id: Int?,
  val manufacturer: String?,
  val manufacturerId: Int?,
  val model: String,
  val modelId: Int,
  val motorCodes: String,
  val name: String?,
  val powerHP: Int,
  val powerKW: Int,
  val yearFrom: String,
  val yearTo: String?
)