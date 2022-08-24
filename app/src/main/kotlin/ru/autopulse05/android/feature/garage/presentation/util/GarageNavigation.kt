package ru.autopulse05.android.feature.garage.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.autopulse05.android.feature.garage.presentation.add.AddToGarageScreen

fun NavGraphBuilder.garageNavigation(navController: NavController) {
  composable(
    route = GarageScreens.Add.route + "?onSuccessNavigate={onSuccessNavigate}",
    arguments = listOf(
      navArgument("onSuccessNavigate") {
        NavType.StringType
      }
    )
  ) { entry ->
    AddToGarageScreen(
      navController = navController,
      onSuccessNavigate = entry.arguments?.getString("onSuccessNavigate")
    )
  }
}