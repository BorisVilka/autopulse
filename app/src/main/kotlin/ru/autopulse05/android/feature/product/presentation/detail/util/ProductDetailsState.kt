package ru.autopulse05.android.feature.product.presentation.detail.util

import ru.autopulse05.android.feature.laximo.data.remote.dto.LaximoApplicationDto
import ru.autopulse05.android.feature.laximo.domain.model.LaximoApplicationInfo
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.domain.model.ProductInfo

data class ProductDetailsState(
  val product: Product? = null,
  val info: ProductInfo? = null,
  val isLoading: Boolean = true,
  val showApplication: Boolean = false,
  val applications: MutableMap<String,MutableMap<String, MutableList<LaximoApplicationInfo>>> = mutableMapOf(),
  val choicedBrand: String = "",
  val choicedModel: String = "",
  val list: MutableList<LaximoApplicationInfo> = mutableListOf(),
  val packing: Int = 0,
  val numberFix: String = "",
  val code: String = "",
  val supplierCode: String = "",
  val description: String = "",
  val quantity: Int = 0,
  val showDeliveryDialog: Boolean = false
)