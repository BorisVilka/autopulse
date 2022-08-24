package ru.autopulse05.android.feature.garage.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
  @PrimaryKey val id: String,
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