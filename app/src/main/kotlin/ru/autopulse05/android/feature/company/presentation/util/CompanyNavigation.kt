package ru.autopulse05.android.feature.company.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.autopulse05.android.feature.company.presentation.about.CompanyAboutScreen
import ru.autopulse05.android.feature.company.presentation.conditions.CompanyConditionsScreen
import ru.autopulse05.android.feature.company.presentation.confidentiality.CompanyConfidentialityScreen
import ru.autopulse05.android.feature.company.presentation.refunds.CompanyRefundsScreen

fun NavGraphBuilder.companyNavigation() {

  composable(
    route = CompanyScreens.About.route
  ) {
    CompanyAboutScreen()
  }
  composable(
    route = CompanyScreens.Conditions.route
  ) {
    CompanyConditionsScreen()
  }
  composable(
    route = CompanyScreens.Confidentiality.route
  ) {
    CompanyConfidentialityScreen()
  }
  composable(
    route = CompanyScreens.Refunds.route
  ) {
    CompanyRefundsScreen()
  }
}