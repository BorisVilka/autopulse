package ru.autopulse05.android.feature.user.presentation.popup.util

import ru.autopulse05.android.R
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabs
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.util.PresentationText

sealed class UserPopupLinks(
  val route: String,
  val title: PresentationText
) {

  object Orders : UserPopupLinks(
    title = PresentationText.Resource(id = R.string.orders),
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Orders.index}")
  )

  object Garage : UserPopupLinks(
    title = PresentationText.Resource(id = R.string.garage),
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Garage.index}")
  )

  object Profile : UserPopupLinks(
    title = PresentationText.Resource(id = R.string.profile),
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Data.index}")
  )
}
