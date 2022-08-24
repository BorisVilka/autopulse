package ru.autopulse05.android.feature.cooperation.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.autopulse05.android.feature.cooperation.presentation.CooperationScreen

fun NavGraphBuilder.cooperationNavigation() {
  composable(
    route = CooperationScreens.Main.route
  ) {
    CooperationScreen()
  }

}