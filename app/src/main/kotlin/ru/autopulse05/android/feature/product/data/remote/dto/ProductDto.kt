package ru.autopulse05.android.feature.product.data.remote.dto

import ru.autopulse05.android.feature.product.domain.model.Product

data class ProductDto(
  val brand: String,
  val number: String,
  val numberFix: String,
  val description: String,
  val availability: Int,
  val packing: Int,
  val deliveryPeriod: Int,
  val deliveryPeriodMax: String,
  val deadlineReplace: String,
  val distributorCode: String,
  val supplierCode: String,
  val supplierColor: String,
  val supplierDescription: String,
  val itemKey: String,
  val price: Double,
  val weight: Double,
//  val volume: Any,
  val deliveryProbability: Float,
  val lastUpdateTime: String,
  val additionalPrice: Int,
  val noReturn: Boolean,
  val distributorId: Int,
//  val grp: Any,
  val code: String
  // val nonliquid: String
)

fun ProductDto.toProduct() =
  Product(
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
//    supplierDescription = supplierDescription,
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
    // nonliquid = nonliquid
  )