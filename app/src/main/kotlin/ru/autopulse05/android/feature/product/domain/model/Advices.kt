package ru.autopulse05.android.feature.product.domain.model


data class Advices(
  val brand: String,
  val number: String,
  val advices: List<Advice>
)