package ru.autopulse05.android.feature.topbar.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
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
import ru.autopulse05.android.feature.topbar.presentation.components.ProfileIcon
import ru.autopulse05.android.feature.topbar.presentation.util.TopBarEvent
import ru.autopulse05.android.feature.topbar.presentation.util.TopBarUiEvent
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.components.Burger
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
        is TopBarUiEvent.OpenDrawerClicked -> scaffoldState.drawerState.open()
        is TopBarUiEvent.OpenUserMenu -> popupState.open()
        is TopBarUiEvent.SignIn -> navController.navigate(UserScreens.SignIn.withArgs("?password="))
      }
    }
  }

  TopAppBar(
    backgroundColor = MaterialTheme.colors.secondary,
    contentColor = MaterialTheme.colors.onSurface
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Burger(
        onClick = {
          viewModel.onEvent(TopBarEvent.OpenDrawerClicked)
        }
      )

      ProfileIcon(
        onClick = {
          viewModel.onEvent(TopBarEvent.ProfileClicked)
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

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
      Row(
        Modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        actions()
      }
    }
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