package ru.autopulse05.android.feature.order.presentation.detail.util

import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.cart.domain.model.Position
import ru.autopulse05.android.feature.order.presentation.util.OrderEvent

sealed class OrderDetailEvent {
    data class InitialValuesChange(val positions: List<Position>) : OrderDetailEvent()
    data class PositionsVisibilityChange(val value: Boolean) : OrderDetailEvent()
}