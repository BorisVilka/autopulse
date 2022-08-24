package ru.autopulse05.android.feature.cart.presentation.util

import ru.autopulse05.android.feature.cart.domain.model.CartItem

sealed class CartUiEvent {
  object NotLoggedIn: CartUiEvent()
  data class Checkout(val positions: List<CartItem>) : CartUiEvent()
  data class Toast(val text: String): CartUiEvent()
}
