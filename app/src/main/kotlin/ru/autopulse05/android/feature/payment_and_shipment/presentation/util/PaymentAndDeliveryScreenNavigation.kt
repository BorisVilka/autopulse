package ru.autopulse05.android.feature.payment_and_shipment.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.autopulse05.android.feature.payment_and_shipment.presentation.PaymentAndShipmentScreen

fun NavGraphBuilder.paymentAndDeliveryScreenNavigation() {
  composable(
    route = PaymentAndShipmentScreens.Main.route
  ) {
    PaymentAndShipmentScreen()
  }

}