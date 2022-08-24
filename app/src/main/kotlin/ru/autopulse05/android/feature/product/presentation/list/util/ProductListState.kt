package ru.autopulse05.android.feature.product.presentation.list.util

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.domain.util.OrderType

data class ProductListState(
  val products: PersistentMap<Product, Int> = persistentMapOf(),
  val isNotFound: Boolean = false,
  val isLoading: Boolean = true,
  val number: String = "",
  val brand: String = "",
  val order: OrderType = OrderType.Ascending,
  val deliveryProbabilityDialogIsShowing: Boolean = false,
  val availabilityFilterIsShowing: Boolean = false,
  val priceFilterIsShowing: Boolean = false
)