package ru.autopulse05.android.feature.product.presentation.list.util

import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.domain.util.OrderType

sealed class ProductListEvent {
  data class InitialValuesChange(val brand: String, val number: String) : ProductListEvent()
  data class OpenProductDetails(val value: Product) : ProductListEvent()
  data class PriceFilterVisibilityChange(val value: Boolean) : ProductListEvent()
  data class AddToBasket(val value: Product): ProductListEvent()
  data class IncreaseQuantityToAdd(val value: Product): ProductListEvent()
  data class DecreaseQuantityToAdd(val value: Product): ProductListEvent()
  data class DeliveryProbabilityDialogVisibilityChange(val value: Boolean): ProductListEvent()
  data class FilterByPriceClick(val value: OrderType) : ProductListEvent()
  data class AvailabilityFilterVisibilityChange(val value: Boolean) : ProductListEvent()
  data class FilterByAvailabilityClick(val value: OrderType) : ProductListEvent()
}