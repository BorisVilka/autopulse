package ru.autopulse05.android.feature.search.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.autopulse05.android.feature.search.presentation.SearchScreen

fun NavGraphBuilder.searchNavigation(
  navController: NavHostController
) {

  composable(
    route = SearchScreens.Main.route + "?number={number}",
    arguments = listOf(
      navArgument("number") {
        NavType.StringType
      },
    )
  ) { entry ->
    SearchScreen(
      navController = navController,
      number = entry.arguments?.getString("number"),
    )
  }
}