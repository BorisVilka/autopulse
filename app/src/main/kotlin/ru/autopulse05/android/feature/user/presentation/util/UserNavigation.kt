package ru.autopulse05.android.feature.user.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.autopulse05.android.feature.garage.presentation.add.AddToGarageScreen
import ru.autopulse05.android.feature.garage.presentation.util.GarageScreens
import ru.autopulse05.android.feature.user.presentation.new_password.NewPasswordScreen
import ru.autopulse05.android.feature.user.presentation.not_signed_in.NotSignedInScreen
import ru.autopulse05.android.feature.user.presentation.profile.ProfileScreen
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabState
import ru.autopulse05.android.feature.user.presentation.restore.RestoreScreen
import ru.autopulse05.android.feature.user.presentation.sign_in.SignInScreen
import ru.autopulse05.android.feature.user.presentation.sign_up.SignUpScreen

fun NavGraphBuilder.userNavigation(
  navController: NavHostController,
  profileTabState: ProfileTabState
) {
  composable(UserScreens.RestorePassword.route) {
    RestoreScreen(navController = navController)
  }
  composable(
    route = UserScreens.NewPassword.route + "?phone={phone}",
    arguments = listOf(
      navArgument("phone") {
        NavType.StringType
      }
    )
  ) { entry ->
    NewPasswordScreen(
      navController = navController,
      phone = entry.arguments?.getString("phone")!!
    )
  }
  composable(
    route = UserScreens.SignIn.route + "?password={password}",
    arguments = listOf(
      navArgument("password") {
        type = NavType.StringType
      }
    )
  ) { entry ->
    SignInScreen(
      navController = navController,
      password = entry.arguments?.getString("password")
    )
  }
  composable(UserScreens.NotSignedIn.route) {
    NotSignedInScreen(navController = navController)
  }
  composable(UserScreens.SignUp.route) {
    SignUpScreen(navController = navController)
  }
  composable(
    route = UserScreens.Profile.route + "?tabIndex={tabIndex}",
    arguments = listOf(
      navArgument("tabIndex") {
        NavType.StringType
      }
    )
  ) { entry ->
    ProfileScreen(
      navController = navController,
      profileTabState = profileTabState,
      tabIndex = entry.arguments?.getString("tabIndex")?.toInt()
    )
  }
  composable(
    route = GarageScreens.Add.route
  ) { entry ->
    AddToGarageScreen(navController = navController, onSuccessNavigate = "")
  }
}