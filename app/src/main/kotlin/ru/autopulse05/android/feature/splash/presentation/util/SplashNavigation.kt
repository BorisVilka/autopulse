package ru.autopulse05.android.feature.splash.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.autopulse05.android.core.presentation.splash.SplashScreen

fun NavGraphBuilder.splashNavigation(
  navController: NavHostController
) {
  composable(
    route = SplashScreens.Main.route
  ) {
    SplashScreen(
      navController = navController,
    )
  }
}