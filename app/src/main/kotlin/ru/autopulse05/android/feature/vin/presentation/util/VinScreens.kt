package ru.autopulse05.android.feature.vin.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class VinScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "vin"
  }

  object Parts : VinScreens("parts")
  object Guest : VinScreens("guest")
  object Car : VinScreens("car")
  object Cars : VinScreens("cars")
  object List: VinScreens("list")
  object Detail: VinScreens("detail")
  object Chat: VinScreens("chat")
}