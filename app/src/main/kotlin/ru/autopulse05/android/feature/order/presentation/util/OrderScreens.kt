package ru.autopulse05.android.feature.order.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class OrderScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "store"
  }

  object Main : OrderScreens("main")

  object Detail: OrderScreens("detail")
}