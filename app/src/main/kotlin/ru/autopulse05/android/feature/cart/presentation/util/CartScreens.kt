package ru.autopulse05.android.feature.cart.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class CartScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "cart"
  }

  object Main : CartScreens("main")
}