package ru.autopulse05.android.feature.product.presentation.crosse.util

sealed class ProductCrosseUiEvent {
  data class Toast(val text: String) : ProductCrosseUiEvent()
}