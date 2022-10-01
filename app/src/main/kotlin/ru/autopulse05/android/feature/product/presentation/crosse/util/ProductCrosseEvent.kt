package ru.autopulse05.android.feature.product.presentation.crosse.util

import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.presentation.detail.util.ProductDetailsEvent

sealed class ProductCrosseEvent {
  data class InitialValuesChange(val crosse: Crosse, val product: Product) : ProductCrosseEvent()
  object AddToBasket: ProductCrosseEvent()
  object IncreaseQuantityToAdd: ProductCrosseEvent()
  object DecreaseQuantityToAdd: ProductCrosseEvent()
  data class OpenCrosseDetails(val value: Crosse) : ProductCrosseEvent()
  data class DeliveryProbabilityDialogVisibilityChange(val value: Boolean) :
    ProductCrosseEvent()
}