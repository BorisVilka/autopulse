package ru.autopulse05.android.core.presentation.topbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.core.presentation.topbar.components.BurgerIcon
import ru.autopulse05.android.core.presentation.topbar.components.ProfileIcon
import ru.autopulse05.android.core.presentation.topbar.util.TopBarEvent
import ru.autopulse05.android.core.presentation.topbar.util.TopBarUiEvent
import ru.autopulse05.android.feature.search.presentation.util.SearchScreens
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.popup.PopupState
import ru.autopulse05.android.shared.presentation.popup.rememberPopupState

@Composable
fun TopBar(
  scaffoldState: ScaffoldState,
  navController: NavController,
  popupState: PopupState,
  viewModel: TopBarViewModel = hiltViewModel(),
  actions: @Composable () -> Unit = {}
) {
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is TopBarUiEvent.OpenDrawer -> scaffoldState.drawerState.open()
        is TopBarUiEvent.OpenUserMenu -> popupState.open()
        is TopBarUiEvent.SignIn -> navController.navigate(UserScreens.SignIn.withArgs("?password="))
        TopBarUiEvent.Search -> navController.navigate(SearchScreens.Main.withArgs("?number="))
      }
    }
  }

  TopAppBar(
    backgroundColor = MaterialTheme.colors.secondary,
    contentColor = MaterialTheme.colors.onSurface
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      BurgerIcon(
        onClick = {
          viewModel.onEvent(TopBarEvent.OpenDrawer)
        }
      )

      ProfileIcon(
        onClick = {
          viewModel.onEvent(TopBarEvent.ProfileClick)
        }
      )
    }

    Row(
      Modifier.fillMaxHeight().weight(1f),
      verticalAlignment = Alignment.CenterVertically
    ) {
      ProvideTextStyle(value = MaterialTheme.typography.h6) {
        CompositionLocalProvider(
          LocalContentAlpha provides ContentAlpha.high,
          content = {}
        )
      }
    }

    actions()
  }
}

@Preview(
  name = "Top Bar",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TopBarPreview() {
  val navController = rememberNavController()
  val scaffoldState = rememberScaffoldState()
  val popupState = rememberPopupState()

  TopBar(
    navController = navController,
    scaffoldState = scaffoldState,
    popupState = popupState
  )
}