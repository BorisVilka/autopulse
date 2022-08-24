package ru.autopulse05.android.feature.navbar.presentation.util

import androidx.annotation.DrawableRes
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.basket.presentation.util.BasketScreens
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabs
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.util.PresentationText

sealed class NavBarItems(
  val title: PresentationText,
  @DrawableRes val icon: Int,
  val route: String
) {

  sealed class NavBarBadgedItems(
    header: PresentationText,
    @DrawableRes icon: Int,
    route: String,
    val badgeText: PresentationText,
    val badgeIsShowing: Boolean
  ) : NavBarItems(
    title = header,
    icon = icon,
    route = route
  )

  object Store : NavBarItems(
    title = PresentationText.Resource(R.string.store),
    icon = R.drawable.ic_store,
    route = StoreScreens.Main.route
  )

  object Garage : NavBarItems(
    title = PresentationText.Resource(R.string.garage),
    icon = R.drawable.ic_car,
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Garage.index}")
  )

  class Basket(
    badgeText: PresentationText,
    badgeIsShowing: Boolean
  ) : NavBarBadgedItems(
    header = PresentationText.Resource(R.string.basket),
    icon = R.drawable.ic_cart,
    route = BasketScreens.Main.route,
    badgeText = badgeText,
    badgeIsShowing = badgeIsShowing
  )

  object Profile : NavBarItems(
    title = PresentationText.Resource(R.string.profile),
    icon = R.drawable.ic_person,
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Data.index}"),
  )
}