package ru.autopulse05.android.core.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class CoreScreens(
  name: String
) : Screens(name = name, prefix = PREFIX) {
  companion object {
    const val PREFIX = "core"
  }

  object Splash: CoreScreens(name = "splash")
}
