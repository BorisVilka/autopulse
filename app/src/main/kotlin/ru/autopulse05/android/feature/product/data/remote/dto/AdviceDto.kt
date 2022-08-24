package ru.autopulse05.android.feature.product.data.remote.dto

import ru.autopulse05.android.feature.product.domain.model.Advice

data class AdviceDto(
  val brand: String,
  val number: String,
  val total: Int
)

fun AdviceDto.toAdvice() =
  Advice(
    brand = brand,
    number = number,
    total = total
  )