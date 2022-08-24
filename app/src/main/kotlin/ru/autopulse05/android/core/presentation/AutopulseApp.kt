package ru.autopulse05.android.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.drawer.Drawer
import ru.autopulse05.android.core.presentation.navbar.NavBar
import ru.autopulse05.android.core.presentation.topbar.TopBar
import ru.autopulse05.android.core.presentation.ui.theme.Autopulse05Theme
import ru.autopulse05.android.core.presentation.ui.theme.BrandDarkGray
import ru.autopulse05.android.core.presentation.ui.theme.BrandLightGray
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.util.CoreScreens
import ru.autopulse05.android.feature.product.presentation.util.ProductScreens
import ru.autopulse05.android.feature.search.presentation.components.SearchBar
import ru.autopulse05.android.feature.search.presentation.util.SearchScreens
import ru.autopulse05.android.feature.user.presentation.popup.UserPopup
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabState
import ru.autopulse05.android.shared.presentation.popup.PopupState

@ExperimentalMaterialApi
@Composable
fun AutopulseApp(
  navController: NavController,
  scaffoldState: ScaffoldState,
  profileTabState: ProfileTabState,
  popupState: PopupState,
  content: @Composable () -> Unit
) {

  val navBackStackEntry = navController.currentBackStackEntryAsState()
  val currentDestination = navBackStackEntry.value?.destination?.route

  Autopulse05Theme {
    Surface {
      Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
          if (
            currentDestination != null &&
            !currentDestination.startsWith(CoreScreens.Splash.route) &&
            !currentDestination.startsWith(SearchScreens.Main.route) &&
            !currentDestination.startsWith(ProductScreens.List.route)
          ) TopBar(
            navController = navController,
            scaffoldState = scaffoldState,
            popupState = popupState
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .background(color = MaterialTheme.colors.secondary)
                .padding(SpaceNormal)
            ) {
              SearchBar(
                value = "",
                onValueChange = { },
                hint = stringResource(id = R.string.detail_number),
                enabled = false,
                modifier = Modifier.clickable {
                  navController.navigate(SearchScreens.Main.withArgs("?number="))
                }
              )
            }
          }
        },
        bottomBar = {
          if (
            currentDestination != null &&
            currentDestination != CoreScreens.Splash.route
          ) NavBar(
            navController = navController,
            profileTabState = profileTabState
          )
        },
        drawerShape = RectangleShape,
        drawerBackgroundColor = Color.BrandDarkGray,
        drawerContentColor = Color.BrandLightGray,
        drawerContent = {
          Drawer(
            navController = navController,
            scaffoldState = scaffoldState
          )
        }
      ) {
        UserPopup(
          popupState = popupState,
          navController = navController
        )

        Box(modifier = Modifier.padding(it)) {
          content()
        }
      }
    }
  }
}