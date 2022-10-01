package ru.autopulse05.android.feature.order.presentation.detail.util

import ru.autopulse05.android.feature.cart.domain.model.Position
import ru.autopulse05.android.feature.user.domain.model.User

data class OrderDetailState(
    val positions: List<Position> = listOf(),
    val isPositionsVisible: Boolean = false,
    val isLoading: Boolean = false,
    val user: User? = null,
)