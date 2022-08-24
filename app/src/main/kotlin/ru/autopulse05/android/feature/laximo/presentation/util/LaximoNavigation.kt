package ru.autopulse05.android.feature.laximo.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.reflect.TypeToken
import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle
import ru.autopulse05.android.feature.laximo.presentation.catalogs.CatalogsScreen
import ru.autopulse05.android.feature.laximo.presentation.categories.LaximoCategoriesScreen
import ru.autopulse05.android.feature.laximo.presentation.unit.LaximoUnitScreen
import ru.autopulse05.android.feature.laximo.presentation.units.LaximoUnitsScreen
import ru.autopulse05.android.feature.laximo.presentation.vehicle.LaximoVehicleScreen
import ru.autopulse05.android.feature.laximo.presentation.vehicles.LaximoVehiclesScreen
import ru.autopulse05.android.shared.data.ext.fromJson

private object LaximoVehicleListToken : TypeToken<List<LaximoVehicle>>()

fun NavGraphBuilder.laximoNavigation(
  navController: NavHostController
) {
  composable(LaximoScreens.Catalogs.route) {
    CatalogsScreen(navController = navController)
  }
  composable(
    route = LaximoScreens.VehicleSearch.route + "?catalog={catalog}",
    arguments = listOf(
      navArgument("catalog") {
        NavType.StringType
      }
    )
  ) { entry ->
    LaximoVehicleScreen(
      navController = navController,
      catalog = entry.arguments?.getString("catalog")
    )
  }
  composable(
    route = LaximoScreens.Vehicles.route + "?vehicles={vehicles}",
    arguments = listOf(
      navArgument("vehicles") {
        NavType.StringType
      }
    )
  ) { entry ->
    LaximoVehiclesScreen(
      navController = navController,
      vehicles = entry.arguments?.getString("vehicles")?.fromJson(LaximoVehicleListToken.type)
    )
  }
  composable(
    route = LaximoScreens.Categories.route +
        "?catalog={catalog}&ssd={ssd}&vehicleId={vehicleId}",
    arguments = listOf(
      navArgument("catalog") {
        NavType.StringType
      },
      navArgument("ssd") {
        NavType.StringType
      },
      navArgument("vehicleId") {
        NavType.StringType
      }
    )
  ) { entry ->
    LaximoCategoriesScreen(
      navController = navController,
      catalog = entry.arguments?.getString("catalog"),
      ssd = entry.arguments?.getString("ssd"),
      vehicleId = entry.arguments?.getString("vehicleId")
    )
  }
  composable(
    route = LaximoScreens.Units.route +
        "?catalog={catalog}&vehicleId={vehicleId}&categoryId={categoryId}&ssd={ssd}",
    arguments = listOf(
      navArgument("catalog") {
        NavType.StringType
      },
      navArgument("vehicleId") {
        NavType.StringType
      },
      navArgument("categoryId") {
        NavType.StringType
      },
      navArgument("ssd") {
        NavType.StringType
      }
    )
  ) { entry ->
    LaximoUnitsScreen(
      navController = navController,
      catalog = entry.arguments?.getString("catalog"),
      ssd = entry.arguments?.getString("ssd"),
      vehicleId = entry.arguments?.getString("vehicleId"),
      categoryId = entry.arguments?.getString("categoryId"),
    )
  }
  composable(
    route = LaximoScreens.Unit.route +
        "?catalog={catalog}&unitId={unitId}&ssd={ssd}",
    arguments = listOf(
      navArgument("catalog") {
        NavType.StringType
      },
      navArgument("unitId") {
        NavType.StringType
      },
      navArgument("ssd") {
        NavType.StringType
      }
    )
  ) { entry ->
    LaximoUnitScreen(
      navController = navController,
      catalog = entry.arguments?.getString("catalog"),
      ssd = entry.arguments?.getString("ssd"),
      unitId = entry.arguments?.getString("unitId"),
    )
  }
}