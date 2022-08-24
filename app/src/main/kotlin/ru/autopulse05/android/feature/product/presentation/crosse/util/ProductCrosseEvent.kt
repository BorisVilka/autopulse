package ru.autopulse05.android.feature.product.presentation.crosse.util

import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.Product

sealed class ProductCrosseEvent {
  data class InitialValuesChange(val crosse: Crosse, val product: Product) : ProductCrosseEvent()
  object AddToBasket: ProductCrosseEvent()
  object IncreaseQuantityToAdd: ProductCrosseEvent()
  object DecreaseQuantityToAdd: ProductCrosseEvent()
}