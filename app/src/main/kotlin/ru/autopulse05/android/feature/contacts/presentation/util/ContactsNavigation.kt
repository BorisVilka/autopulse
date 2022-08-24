package ru.autopulse05.android.feature.contacts.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.autopulse05.android.feature.contacts.presentation.ContactsScreen

fun NavGraphBuilder.contactsNavigation() {
  composable(
    route = ContactsScreens.Main.route
  ) {
    ContactsScreen()
  }

}