package ru.autopulse05.android.feature.user.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class UserScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "user"
  }

  object RestorePassword : UserScreens("restore_password")
  object NewPassword : UserScreens("new_password")
  object SignIn : UserScreens("sign_in")
  object SignUp : UserScreens("sign_up")
  object NotSignedIn : UserScreens("not_signed_in")
  object Profile : UserScreens("profile")
}