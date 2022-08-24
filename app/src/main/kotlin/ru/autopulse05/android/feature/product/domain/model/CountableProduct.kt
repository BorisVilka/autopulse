package ru.autopulse05.android.feature.product.domain.model

import ru.autopulse05.android.feature.basket.domain.model.Position

data class CountableProduct(
  override val brand: String,
  override val number: String,
  override val numberFix: String,
  override val description: String,
  override val availability: Int,
  override val packing: Int,
  override val deliveryPeriod: Int,
  override val deliveryPeriodMax: String,
  override val deadlineReplace: String,
  override val distributorCode: String,
  override val supplierCode: String,
  override val supplierColor: String,
  val supplierDescription: String,
  override val itemKey: String,
  override val price: Double,
  override val weight: Double,
//  volume: Any,
   override val deliveryProbability: Float,
   override val lastUpdateTime: String,
   override val additionalPrice: Int,
   override val noReturn: Boolean,
   override val distributorId: Int,
//  grp: Any,
  override val code: String,
//  nonliquid: String,
  val quantity: Int
) : Product(
  brand = brand,
  number = number,
  numberFix = numberFix,
  description = description,
  availability = availability,
  packing = packing,
  deliveryPeriod = deliveryPeriod,
  deliveryPeriodMax = deliveryPeriodMax,
  deadlineReplace = deadlineReplace,
  distributorCode = distributorCode,
  supplierCode = supplierCode,
  supplierColor = supplierColor,
  itemKey = itemKey,
  price = price,
  weight = weight,
//    volume = volume,
  deliveryProbability = deliveryProbability,
  lastUpdateTime = lastUpdateTime,
  additionalPrice = additionalPrice,
  noReturn = noReturn,
  distributorId = distributorId,
//    grp = grp,
  code = code
//  nonliquid = nonliquid,
)

fun CountableProduct.toPosition(
  comment: String = ""
) = Position(
  brand = brand,
  number = number,
  numberFix = numberFix,
  code = code,
  supplierCode = supplierCode,
  description = description,
  quantity = quantity,
  comment = comment,
)