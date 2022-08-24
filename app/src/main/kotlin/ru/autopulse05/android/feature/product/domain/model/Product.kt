package ru.autopulse05.android.feature.product.domain.model

import ru.autopulse05.android.feature.cart.domain.model.Position


open class Product(
    open val brand: String,
    open val number: String,
    open val numberFix: String,
    open val description: String,
    open val availability: Int,
    open val packing: Int,
    open val deliveryPeriod: Int,
    open val deliveryPeriodMax: String,
    open val deadlineReplace: String,
    open val distributorCode: String,
    open val supplierCode: String,
    open val supplierColor: String,
//  val supplierDescription: String,
    open val itemKey: String,
    open val price: Double,
    open val weight: Double,
//  val volume: Any,
    open val deliveryProbability: Float,
    open val lastUpdateTime: String,
    open val additionalPrice: Int,
    open val noReturn: Boolean,
    open val distributorId: Int,
//  val grp: Any,
    open val code: String,
  // val nonliquid: String
)

fun Product.toPosition(quantity: Int) = Position(
  brand = brand,
  number = number,
  numberFix = numberFix,
  code = code,
  supplierCode = supplierCode,
  description = description,
  quantity = quantity,
  comment = "",
    itemKey = itemKey,
    status = "",
    price = price.toString()
)