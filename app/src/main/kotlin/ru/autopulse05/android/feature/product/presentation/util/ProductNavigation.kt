package ru.autopulse05.android.feature.product.presentation.util

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.presentation.crosse.CrosseScreen
import ru.autopulse05.android.feature.product.presentation.detail.DetailScreen
import ru.autopulse05.android.feature.product.presentation.list.ProductListScreen
import ru.autopulse05.android.shared.data.ext.fromJson
import ru.autopulse05.android.shared.presentation.popup.PopupState

fun NavGraphBuilder.productNavigation(
  navController: NavHostController,
  scaffoldState: ScaffoldState,
  popupState: PopupState
) {
  composable(
    route = ProductScreens.List.route + "?brand={brand}&number={number}",
    arguments = listOf(
      navArgument("brand") {
        NavType.StringType
      },
      navArgument("number") {
        NavType.StringType
      }
    )
  ) { entry ->
    ProductListScreen(
      navController = navController,
      scaffoldState = scaffoldState,
      brand = entry.arguments?.getString("brand"),
      number = entry.arguments?.getString("number"),
      popupState = popupState
    )
  }
  composable(
    route = ProductScreens.Detail.route + "?product={product}",
    arguments = listOf(
      navArgument("product") {
        NavType.StringType
      }
    )
  ) { entry ->
    DetailScreen(
      navController = navController,
      product = entry.arguments?.getString("product")?.fromJson(Product::class.java)
    )
  }
  composable(
    route = ProductScreens.Crosse.route + "?crosse={crosse}&product={product}",
    arguments = listOf(
      navArgument("crosse") {
        NavType.StringType
      },
      navArgument("product") {
        NavType.StringType
      }
    )
  ) { entry ->
    CrosseScreen(
      crosse = entry.arguments?.getString("crosse")?.fromJson(Crosse::class.java),
      product = entry.arguments?.getString("product")?.fromJson(Product::class.java)
    )
  }
}