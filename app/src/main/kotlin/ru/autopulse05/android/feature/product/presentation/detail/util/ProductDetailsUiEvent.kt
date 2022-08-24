package ru.autopulse05.android.feature.product.presentation.detail.util

import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.Product

sealed class ProductDetailsUiEvent {
  data class GoToCrosse(val crosse: Crosse, val product: Product): ProductDetailsUiEvent()
  data class Toast(val text: String) : ProductDetailsUiEvent()
}