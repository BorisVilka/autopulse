package ru.autopulse05.android.feature.vin.domain.model

sealed class StockMode(val value: Int) {
  object Enable : StockMode(value = 1)
  object Disable : StockMode(value = 0)
}
