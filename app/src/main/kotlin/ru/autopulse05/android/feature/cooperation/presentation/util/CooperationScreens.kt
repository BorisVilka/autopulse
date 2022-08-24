package ru.autopulse05.android.feature.cooperation.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class CooperationScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "cooperation"
  }

  object Main : CooperationScreens("main")
}