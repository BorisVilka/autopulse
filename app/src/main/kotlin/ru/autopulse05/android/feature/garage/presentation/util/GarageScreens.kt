package ru.autopulse05.android.feature.garage.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class GarageScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "garage"
  }

  object Add : GarageScreens("add")
}