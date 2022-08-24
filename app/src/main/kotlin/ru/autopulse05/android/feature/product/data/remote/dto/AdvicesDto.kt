package ru.autopulse05.android.feature.product.data.remote.dto

import ru.autopulse05.android.feature.product.domain.model.Advices


data class AdvicesDto(
  val brand: String,
  val number: String,
  val advices: List<AdviceDto>
)

fun AdvicesDto.toAdvices() =
  Advices(
    brand = brand,
    number = number,
    advices = advices.map { it.toAdvice() }
  )