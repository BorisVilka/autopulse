package ru.autopulse05.android.core.presentation.drawer.util

import ru.autopulse05.android.R
import ru.autopulse05.android.feature.info.presentation.util.InfoScreens
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabs
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.util.PresentationText

sealed class DrawerItems(
  val header: PresentationText
) {
  sealed class ExpandableItem(
    val index: Int,
    header: PresentationText,
    val children: List<LinkItem>
  ) : DrawerItems(
    header = header
  )

  sealed class LinkItem(
    header: PresentationText,
    val route: String
  ) : DrawerItems(
    header = header
  )

  // Profile
  object Profile : ExpandableItem(
    header = PresentationText.Resource(R.string.personal_cabinet),
    children = listOf(
      Garage,
      Orders,
      PersonalData
    ),
    index = 0
  )

  object Garage : LinkItem(
    header = PresentationText.Resource(R.string.garage),
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Garage.index}")
  )

  object Orders : LinkItem(
    header = PresentationText.Resource(R.string.orders),
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Orders.index}")
  )

  object PersonalData : LinkItem(
    header = PresentationText.Resource(R.string.personal_data),
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Data.index}")
  )

  // Company
  object Company : ExpandableItem(
    header = PresentationText.Resource(R.string.company),
    children = listOf(
      AboutCompany,
      Conditions,
      Refunds,
      Confidentiality
    ),
    index = 1
  )

  object AboutCompany : LinkItem(
    header = PresentationText.Resource(R.string.about_company),
    route = InfoScreens.About.route
  )

  object Conditions : LinkItem(
    header = PresentationText.Resource(R.string.conditions),
    route = InfoScreens.Conditions.route
  )

  object Refunds : LinkItem(
    header = PresentationText.Resource(R.string.refunds),
    route = InfoScreens.Refunds.route
  )

  object Confidentiality : LinkItem(
    header = PresentationText.Resource(R.string.confidentiality),
    route = InfoScreens.Confidentiality.route
  )

  // PaymentAndShipment
  object PaymentAndShipment : LinkItem(
    header = PresentationText.Resource(R.string.payment_and_shipment),
    route = InfoScreens.PaymentAndShipment.route
  )

  // Cooperation
  object Cooperation : LinkItem(
    header = PresentationText.Resource(R.string.cooperation),
    route = InfoScreens.Cooperation.route
  )

  // Contacts
  object Contacts : LinkItem(
    header = PresentationText.Resource(R.string.contacts),
    route = InfoScreens.Contacts.route
  )
}
