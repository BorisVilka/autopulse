package ru.autopulse05.android.feature.order.presentation.detail.util

import ru.autopulse05.android.feature.cart.domain.model.Position

data class OrderDetailState(
    val positions: List<Position> = listOf(),
    val isPositionsVisible: Boolean = false,
)