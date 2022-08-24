package ru.autopulse05.android.feature.vin.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.reflect.TypeToken
import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.vin.domain.model.CarInfo
import ru.autopulse05.android.feature.vin.presentation.car.VinCarScreen
import ru.autopulse05.android.feature.vin.presentation.cars.VinCarsScreen
import ru.autopulse05.android.feature.vin.presentation.guest.VinGuestScreen
import ru.autopulse05.android.feature.vin.presentation.parts.VinPartsScreen
import ru.autopulse05.android.shared.data.ext.fromJson

private object PartsListToken: TypeToken<List<String>>()

fun NavGraphBuilder.vinNavigation(navController: NavController) {
  composable(
    route = VinScreens.Car.route + "?parts={parts}?car={car}",
    arguments = listOf(
      navArgument("parts") {
        NavType.StringType
      },
      navArgument("car") {
        NavType.StringType
      }
    )
  ) { entry ->
    VinCarScreen(
      navController = navController,
      parts = entry.arguments?.getString("parts")?.fromJson(PartsListToken.type),
      car = entry.arguments?.getString("car")?.fromJson(Car::class.java)
    )
  }
  composable(
    route = VinScreens.Cars.route + "?parts={parts}",
    arguments = listOf(
      navArgument("parts") {
        NavType.StringType
      }
    )
  ) { entry ->
    VinCarsScreen(
      navController = navController,
      parts = entry.arguments?.getString("parts")?.fromJson(PartsListToken.type)
    )
  }
  composable(VinScreens.Parts.route) {
    VinPartsScreen(navController = navController)
  }
  composable(
    route = VinScreens.Guest.route + "?parts={parts}&carInfo={carInfo}",
    arguments = listOf(
      navArgument("carInfo") {
        NavType.StringType
      },
      navArgument("parts") {
        NavType.StringType
      }
    )
  ) { entry ->
    VinGuestScreen(
      navController = navController,
      carInfo = entry.arguments?.getString("carInfo")?.fromJson(CarInfo::class.java),
      parts = entry.arguments?.getString("parts")?.fromJson(PartsListToken.type)
    )
  }
}