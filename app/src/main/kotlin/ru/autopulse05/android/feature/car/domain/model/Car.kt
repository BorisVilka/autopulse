package ru.autopulse05.android.feature.car.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
  @PrimaryKey val id: String,
  val name: String?,
  val comment: String?,
  val year: Int?,
  val vin: String?,
  val frame: String?,
  val mileage: String?,
  val manufacturerId: Int?,
  val manufacturer: String?,
  val modelId: Int?,
  val modelName: String?,
  val modificationName: String?,
  val modificationId: Int?,
  val vehicleRegPlate: String?
) {
  val model: CarModel
    get() = CarModel(
      id = modelId,
      name = modelName,
      yearTo = "",
      yearFrom = ""
    )

  val mark: CarMark
    get() = CarMark(
      id = manufacturerId,
      name = manufacturer
    )

  val modification: CarModification
    get() = CarModification(
      constructionType = "",
      cylinderCapacityCcm = 0,
      fuelType = "",
      fullName = "",
      id = modificationId,
      manufacturer = manufacturer,
      manufacturerId = manufacturerId,
      model = "",
      modelId = 0,
      motorCodes = "",
      name = modificationName,
      powerHP = 0,
      powerKW = 0,
      yearFrom = "",
      yearTo = ""
    )
}

