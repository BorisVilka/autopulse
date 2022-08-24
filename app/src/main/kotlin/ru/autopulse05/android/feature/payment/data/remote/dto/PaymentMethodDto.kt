package ru.autopulse05.android.feature.payment.data.remote.dto

import ru.autopulse05.android.feature.payment.domain.model.PaymentMethod


data class PaymentMethodDto(
  val id: String,
  val name: String
)

fun PaymentMethodDto.toPaymentMethod() = PaymentMethod(
  id = id,
  name = name
)