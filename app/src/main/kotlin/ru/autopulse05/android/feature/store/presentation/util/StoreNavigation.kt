package ru.autopulse05.android.feature.store.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.autopulse05.android.feature.store.presentation.StoreScreen

fun NavGraphBuilder.storeNavigation(
  navController: NavHostController
) {
  composable(
    route = StoreScreens.Main.route
  ) {
    StoreScreen(
      navController = navController
    )
  }
}