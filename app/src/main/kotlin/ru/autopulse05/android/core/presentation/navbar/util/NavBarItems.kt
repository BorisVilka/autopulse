package ru.autopulse05.android.core.presentation.navbar.util

import androidx.annotation.DrawableRes
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.cart.presentation.util.CartScreens
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
    icon = R.mipmap.shop_foreground,
    route = StoreScreens.Main.route
  )

  object Garage : NavBarItems(
    title = PresentationText.Resource(R.string.garage),
    icon = R.mipmap.garage_foreground,
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Garage.index}")
  )

  object Orders : NavBarItems(
    title = PresentationText.Dynamic("Заказы"),
    icon = R.drawable.ic_outline_event_note_24,
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Orders.index}")
  )

  class Basket(
    badgeText: PresentationText,
    badgeIsShowing: Boolean
  ) : NavBarBadgedItems(
    header = PresentationText.Resource(R.string.basket),
    icon = R.mipmap.basket_foreground,
    route = CartScreens.Main.route,
    badgeText = badgeText,
    badgeIsShowing = badgeIsShowing
  )

  object Profile : NavBarItems(
    title = PresentationText.Resource(R.string.profile),
    icon = R.mipmap.person_foreground,
    route = UserScreens.Profile.withArgs("?tabIndex=${ProfileTabs.Data.index}"),
  )
}