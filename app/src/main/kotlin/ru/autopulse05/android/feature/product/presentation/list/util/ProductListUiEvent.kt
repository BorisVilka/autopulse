package ru.autopulse05.android.feature.product.presentation.list.util

import ru.autopulse05.android.feature.product.domain.model.Product

sealed class ProductListUiEvent {
  data class GoToProductDetails(val value: Product): ProductListUiEvent()
  data class Toast(val text: String): ProductListUiEvent()
}