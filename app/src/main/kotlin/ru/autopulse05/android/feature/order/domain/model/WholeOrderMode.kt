package ru.autopulse05.android.feature.order.domain.model

sealed class WholeOrderMode(val value: Int) {
  object On: WholeOrderMode(value = 1)
  object Off: WholeOrderMode(value = 0)
}
