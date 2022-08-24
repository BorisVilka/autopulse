package ru.autopulse05.android.feature.cart.data.remote.dto

import ru.autopulse05.android.feature.cart.domain.model.CartItem


data class CartItemDto(
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
  val status: Int,
  val positionId: Int,
  val packing: Int,
  val errorMessage: String
)

fun CartItemDto.toCartItem() = CartItem(
  id = "${brand}-${number}",
  brand = brand,
  number = number,
  numberFix = numberFix,
  code = code,
  supplierCode = supplierCode,
  itemKey = itemKey,
  description = description,
  price = price,
  priceRate = priceRate,
  priceInSiteCurrency = priceInSiteCurrency,
  quantity = quantity,
  deadline = deadline,
  deadlineMax = deadlineMax,
  comment = comment,
  positionId = positionId,
  packing = packing
)