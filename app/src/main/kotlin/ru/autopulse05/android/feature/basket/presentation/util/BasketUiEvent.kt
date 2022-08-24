package ru.autopulse05.android.feature.basket.presentation.util

import ru.autopulse05.android.feature.basket.domain.model.BasketItem

sealed class BasketUiEvent {
  object NotLoggedIn: BasketUiEvent()
  data class Checkout(val positions: List<BasketItem>) : BasketUiEvent()
  data class Toast(val text: String): BasketUiEvent()
}
