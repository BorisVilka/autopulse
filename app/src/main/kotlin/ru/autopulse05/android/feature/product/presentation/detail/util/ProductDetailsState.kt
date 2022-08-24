package ru.autopulse05.android.feature.product.presentation.detail.util

import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.domain.model.ProductInfo

data class ProductDetailsState(
  val product: Product? = null,
  val info: ProductInfo? = null,
  val isLoading: Boolean = true,
  val packing: Int = 0,
  val numberFix: String = "",
  val code: String = "",
  val supplierCode: String = "",
  val description: String = "",
  val quantity: Int = 0
)