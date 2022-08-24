package ru.autopulse05.android.feature.cart.presentation.util

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import ru.autopulse05.android.feature.cart.domain.model.CartItem

data class CartState(
  val isLoading: Boolean = true,
  val selected: PersistentList<CartItem> = persistentListOf(),
  val items: List<CartItem> = listOf()
)
