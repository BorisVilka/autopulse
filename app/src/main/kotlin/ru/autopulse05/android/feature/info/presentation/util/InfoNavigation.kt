package ru.autopulse05.android.feature.info.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.autopulse05.android.feature.info.presentation.about.AboutScreen
import ru.autopulse05.android.feature.info.presentation.conditions.ConditionsScreen
import ru.autopulse05.android.feature.info.presentation.confidentiality.ConfidentialityScreen
import ru.autopulse05.android.feature.info.presentation.contacts.ContactsScreen
import ru.autopulse05.android.feature.info.presentation.coperation.CooperationScreen
import ru.autopulse05.android.feature.info.presentation.payment_and_shipment.PaymentAndShipmentScreen
import ru.autopulse05.android.feature.info.presentation.refunds.RefundsScreen

fun NavGraphBuilder.infoNavigation() {

  composable(
    route = InfoScreens.About.route
  ) {
    AboutScreen()
  }
  composable(
    route = InfoScreens.Confidentiality.route
  ) {
    ConfidentialityScreen()
  }
  composable(
    route = InfoScreens.Conditions.route
  ) {
    ConditionsScreen()
  }
  composable(
    route = InfoScreens.Contacts.route
  ) {
    ContactsScreen()
  }
  composable(
    route = InfoScreens.Cooperation.route
  ) {
    CooperationScreen()
  }
  composable(
    route = InfoScreens.PaymentAndShipment.route
  ) {
    PaymentAndShipmentScreen()
  }
  composable(
    route = InfoScreens.Refunds.route
  ) {
    RefundsScreen()
  }
}