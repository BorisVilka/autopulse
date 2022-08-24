package ru.autopulse05.android.core.presentation.navbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.core.presentation.navbar.util.NavBarEvent
import ru.autopulse05.android.core.presentation.navbar.util.NavBarItems
import ru.autopulse05.android.core.presentation.navbar.util.NavBarUiEvent
import ru.autopulse05.android.core.presentation.ui.theme.BrandOrange
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabState
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabs
import ru.autopulse05.android.feature.user.presentation.profile.util.rememberProfileTabState
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun NavBar(
  navController: NavController,
  profileTabState: ProfileTabState,
  viewModel: NavBarViewModel = hiltViewModel()
) {

  val navBackStackEntry = navController.currentBackStackEntryAsState()
  val currentDestination = navBackStackEntry.value?.destination?.route
  val context = LocalContext.current
  val state = viewModel.state

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is NavBarUiEvent.OnNavBarItemClick -> navController.navigate(event.value.route)
      }
    }
  }

  val items = listOf(
    NavBarItems.Store,
    NavBarItems.Garage,
    NavBarItems.Basket(
      badgeText = PresentationText.Dynamic(state.basketItemsCount.toString()),
      badgeIsShowing = state.basketItemsCount > 0
    ),
    NavBarItems.Profile
  )

  BottomNavigation(backgroundColor = MaterialTheme.colors.secondary) {
    items.forEach {

        BottomNavigationItem(
          selected = when (it) {
            is NavBarItems.Profile -> currentDestination?.startsWith(UserScreens.Profile.route) ?: false &&
                profileTabState.current == ProfileTabs.Data
            is NavBarItems.Garage -> currentDestination?.startsWith(UserScreens.Profile.route) ?: false &&
                profileTabState.current == ProfileTabs.Garage
            else -> currentDestination?.startsWith(it.route) ?: false
          },
          selectedContentColor = Color.BrandYellow,
          unselectedContentColor = MaterialTheme.colors.onSurface,
          icon = {
            BadgedBox(
              badge = {
                if (it is NavBarItems.NavBarBadgedItems && it.badgeIsShowing) {
                  Badge(
                    backgroundColor = Color.BrandOrange
                  ) {
                    Text(
                      text = it.badgeText.asString()
                    )
                  }
                }
              }
            ) {
              Icon(
                painter = painterResource(id = it.icon),
                modifier = Modifier.width(24.dp).height(24.dp),
                contentDescription = it.title.asString()
              )
            }
          },
          label = {
            Text(
              text = it.title.asString(),
              style = MaterialTheme.typography.caption,
              fontSize = 10.sp,
              maxLines = 1
            )
          },
          onClick = {
            viewModel.onEvent(NavBarEvent.OnNavBarItemClick(value = it))
          },
          modifier = Modifier.weight(0.2f)
        )
      }
    }

}

@Preview(
  name = "Nav Bar",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun NavBarPreview() {
  val navController = rememberNavController()
  val profileTabState = rememberProfileTabState()

  NavBar(
    navController = navController,
    profileTabState = profileTabState
  )
}