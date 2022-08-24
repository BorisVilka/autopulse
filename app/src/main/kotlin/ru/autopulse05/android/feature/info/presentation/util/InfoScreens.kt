package ru.autopulse05.android.feature.info.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class InfoScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "info"
  }

  object About : InfoScreens("about")
  object Conditions : InfoScreens("conditions")
  object Confidentiality : InfoScreens("confidentiality")
  object Contacts : InfoScreens("contacts")
  object Cooperation : InfoScreens("cooperation")
  object PaymentAndShipment : InfoScreens("payment_and_shipment")
  object Refunds : InfoScreens("refunds")
}