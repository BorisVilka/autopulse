package ru.autopulse05.android.feature.basket.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.autopulse05.android.feature.basket.presentation.BasketScreen

fun NavGraphBuilder.basketNavigation(navController: NavController) {
  composable(
    route = BasketScreens.Main.route
  ) {
    BasketScreen(navController = navController)
  }
}