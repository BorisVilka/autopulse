package ru.autopulse05.android.feature.payment_and_shipment.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shipment_addresses")
data class ShipmentAddress(
  @PrimaryKey val id: String,
  val name: String
)