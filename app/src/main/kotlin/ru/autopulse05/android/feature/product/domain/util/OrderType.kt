package ru.autopulse05.android.feature.product.domain.util

sealed class OrderType {
  object Ascending : OrderType()
  object Descending : OrderType()
}
