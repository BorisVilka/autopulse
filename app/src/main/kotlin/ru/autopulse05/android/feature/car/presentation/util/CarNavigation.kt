package ru.autopulse05.android.feature.car.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.car.presentation.edit.CarEditScreen
import ru.autopulse05.android.shared.data.ext.fromJson

fun NavGraphBuilder.carNavigation(navController: NavController) {
  composable(
    route = CarScreens.Edit.route + "?car={car}",
    arguments = listOf(
      navArgument("car") {
        NavType.StringType
      }
    )
  ) { entry ->
    CarEditScreen(
      navController = navController,
      car = entry.arguments?.getString("car")?.fromJson(Car::class.java)
    )
  }
}