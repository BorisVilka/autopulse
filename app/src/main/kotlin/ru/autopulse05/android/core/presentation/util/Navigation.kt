package ru.autopulse05.android.core.presentation.util

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.feature.car.presentation.util.carNavigation
import ru.autopulse05.android.feature.cart.presentation.util.cartNavigation
import ru.autopulse05.android.feature.info.presentation.util.infoNavigation
import ru.autopulse05.android.feature.laximo.presentation.util.laximoNavigation
import ru.autopulse05.android.feature.order.presentation.util.orderNavigation
import ru.autopulse05.android.feature.product.presentation.util.productNavigation
import ru.autopulse05.android.feature.search.presentation.util.searchNavigation
import ru.autopulse05.android.core.presentation.splash.SplashScreen
import ru.autopulse05.android.feature.store.presentation.util.storeNavigation
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabState
import ru.autopulse05.android.feature.user.presentation.profile.util.rememberProfileTabState
import ru.autopulse05.android.feature.user.presentation.util.userNavigation
import ru.autopulse05.android.feature.vin.presentation.util.vinNavigation
import ru.autopulse05.android.shared.presentation.popup.PopupState
import ru.autopulse05.android.shared.presentation.popup.rememberPopupState

@Composable
fun Navigation(
  navController: NavHostController,
  scaffoldState: ScaffoldState,
  popupState: PopupState,
  profileTabState: ProfileTabState
) {
  NavHost(
    navController = navController,
    startDestination = CoreScreens.Splash.route
  ) {

    composable(CoreScreens.Splash.route) {
      SplashScreen(navController = navController)
    }

    infoNavigation()
    cartNavigation(navController = navController)
    searchNavigation(navController = navController)
    userNavigation(navController = navController, profileTabState = profileTabState)
    productNavigation(
      navController = navController,
      scaffoldState = scaffoldState,
      popupState = popupState
    )
    storeNavigation(navController = navController)
    laximoNavigation(navController = navController)
    vinNavigation(navController = navController)
    orderNavigation(navController = navController)
    carNavigation(navController = navController)
  }
}

@Preview
@Composable
fun NavigationPreview() {
  val navController = rememberNavController()
  val scaffoldState = rememberScaffoldState()
  val popupState = rememberPopupState()
  val profileTabState = rememberProfileTabState()

  Navigation(
    navController = navController,
    scaffoldState = scaffoldState,
    popupState = popupState,
    profileTabState = profileTabState
  )
}