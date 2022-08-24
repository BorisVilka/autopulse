package ru.autopulse05.android.feature.splash.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class SplashScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "splash"
  }

  object Main : SplashScreens("main")
}