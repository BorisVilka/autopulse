package ru.autopulse05.android.feature.product.presentation.crosse.util

import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.presentation.detail.util.ProductDetailsUiEvent

sealed class ProductCrosseUiEvent {
  data class Toast(val text: String) : ProductCrosseUiEvent()
  data class GoToCrosse(val crosse: Crosse, val product: Product): ProductCrosseUiEvent()
}