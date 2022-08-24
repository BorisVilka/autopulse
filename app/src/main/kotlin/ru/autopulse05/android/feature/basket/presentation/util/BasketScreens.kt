package ru.autopulse05.android.feature.basket.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class BasketScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "basket"
  }

  object Main : BasketScreens("main")
}