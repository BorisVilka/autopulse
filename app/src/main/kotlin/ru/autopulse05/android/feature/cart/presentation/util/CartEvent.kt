package ru.autopulse05.android.feature.cart.presentation.util

import ru.autopulse05.android.feature.cart.domain.model.CartItem

sealed class CartEvent {
  data class SelectAllChange(val value: Boolean) : CartEvent()
  data class SelectChange(val item: CartItem, val value: Boolean) : CartEvent()
  object Checkout : CartEvent()
  object Clear : CartEvent()
}