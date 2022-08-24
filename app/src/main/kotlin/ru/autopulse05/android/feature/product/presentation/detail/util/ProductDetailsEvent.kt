package ru.autopulse05.android.feature.product.presentation.detail.util

import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.Product

sealed class ProductDetailsEvent {
  data class InitialValuesChange(val product: Product) : ProductDetailsEvent()
  object AddToBasket: ProductDetailsEvent()
  object IncreaseQuantityToAdd: ProductDetailsEvent()
  object DecreaseQuantityToAdd: ProductDetailsEvent()
  data class OpenCrosseDetails(val value: Crosse) : ProductDetailsEvent()
}