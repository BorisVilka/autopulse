package ru.autopulse05.android.feature.payment_and_shipment.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class PaymentAndShipmentScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "payment_and_shipment"
  }

  object Main : PaymentAndShipmentScreens("main")
}