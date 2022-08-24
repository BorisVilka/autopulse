package ru.autopulse05.android.feature.basket.presentation.util

import ru.autopulse05.android.feature.basket.domain.model.BasketItem

sealed class BasketEvent {
  data class SelectAllChange(val value: Boolean) : BasketEvent()
  data class SelectChange(val item: BasketItem, val value: Boolean) : BasketEvent()
  object Checkout : BasketEvent()
  object Clear : BasketEvent()
}