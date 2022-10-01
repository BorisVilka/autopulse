package ru.autopulse05.android.feature.product.presentation.crosse.util

import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.domain.model.ProductInfo

data class ProductCrosseState(
  val crosse: Crosse? = null,
  val product: Product? = null,
  val info: ProductInfo? = null,
  val isLoading: Boolean = true,
  val packing: Int = 0,
  val numberFix: String = "",
  val code: String = "",
  val supplierCode: String = "",
  val description: String = "",
  val quantity: Int = 0,
  val showDeliveryDialog: Boolean = false
)