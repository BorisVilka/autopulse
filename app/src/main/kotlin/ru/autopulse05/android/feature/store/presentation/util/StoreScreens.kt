package ru.autopulse05.android.feature.store.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class StoreScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "store"
  }

  object Main : StoreScreens("main")
}