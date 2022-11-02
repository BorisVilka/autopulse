package ru.autopulse05.android.feature.user.presentation.profile.util

import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.order.domain.model.Order
import ru.autopulse05.android.feature.order.domain.model.Picking
import ru.autopulse05.android.feature.order.domain.model.Refund
import ru.autopulse05.android.feature.order.domain.model.TsOrder
import ru.autopulse05.android.feature.user.data.remote.dto.PaymentDto
import ru.autopulse05.android.feature.user.domain.model.User


data class ProfileState(
  val user: User? = null,
  val cars: List<Car> = listOf(),
  val orders: List<Order> = listOf(),
  val pickings: List<Picking> = listOf(),
  val refunds: List<Refund> = listOf(),
  val payments: List<PaymentDto> = listOf(),
  val date: String = "",
  val dateM: String = "",
  val filter: String = "",
  val isNotFound: Boolean = false,
  val isLoading: Boolean = true
)