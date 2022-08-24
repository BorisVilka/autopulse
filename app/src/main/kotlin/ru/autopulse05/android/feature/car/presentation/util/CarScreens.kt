package ru.autopulse05.android.feature.car.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class CarScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "car"
  }

  object Edit : CarScreens("add")
}