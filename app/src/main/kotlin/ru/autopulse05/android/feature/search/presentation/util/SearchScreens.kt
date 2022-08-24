package ru.autopulse05.android.feature.search.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class SearchScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "search"
  }

  object Main : SearchScreens("main")
}