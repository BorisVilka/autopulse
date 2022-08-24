package ru.autopulse05.android.feature.product.domain.model

import ru.autopulse05.android.feature.cart.domain.model.Position
import ru.autopulse05.android.shared.domain.model.Image

data class ProductInfo(
  val brand: String,
  val number: String,
  val outerNumber: String?,
  val properties: Properties,
  val crosses: List<Crosse>,
  val images: List<Image>
)

fun ProductInfo.toPosition(
  quantity: Int,
  numberFix: String,
  code: String,
  supplierCode: String,
  description: String,
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
  itemKey = "",
  status = "",
  price = ""
)