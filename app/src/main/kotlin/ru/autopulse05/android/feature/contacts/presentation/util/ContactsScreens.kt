package ru.autopulse05.android.feature.contacts.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class ContactsScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "contacts"
  }

  object Main : ContactsScreens("main")
}