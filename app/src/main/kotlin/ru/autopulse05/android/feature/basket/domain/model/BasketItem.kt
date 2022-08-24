package ru.autopulse05.android.feature.basket.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_items")
data class BasketItem(
  @PrimaryKey val id: String,
  val brand: String,
  val number: String,
  val numberFix: String,
  val code: String?,
  val supplierCode: String,
  val itemKey: String,
  val description: String,
  val price: String,
  val priceRate: String,
  val priceInSiteCurrency: Int,
  val quantity: Int,
  val deadline: String,
  val deadlineMax: String,
  val comment: String,
  val positionId: Int,
  val packing: Int
)