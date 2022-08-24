package ru.autopulse05.android.feature.cart.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.autopulse05.android.feature.cart.presentation.CartScreen

fun NavGraphBuilder.cartNavigation(navController: NavController) {
  composable(
    route = CartScreens.Main.route
  ) {
    CartScreen(navController = navController)
  }
}