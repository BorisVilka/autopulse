package ru.autopulse05.android.feature.drawer.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.feature.drawer.presentation.components.ExpandableItem
import ru.autopulse05.android.feature.drawer.presentation.components.LinkItem
import ru.autopulse05.android.feature.drawer.presentation.util.DrawerEvent
import ru.autopulse05.android.feature.drawer.presentation.util.DrawerItems
import ru.autopulse05.android.feature.drawer.presentation.util.DrawerUiEvent

@Composable
fun Drawer(
  scaffoldState: ScaffoldState,
  navController: NavController,
  viewModel: DrawerViewModel = hiltViewModel()
) {
  val state = viewModel.state
  val context = LocalContext.current
  val items = listOf(
    DrawerItems.Profile,
    DrawerItems.Company,
    DrawerItems.PaymentAndShipment,
    DrawerItems.Cooperation,
    DrawerItems.Contacts
  )

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(DrawerEvent.OnExpandItemClick(index = DrawerItems.Profile.index))
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is DrawerUiEvent.OnLinkItemClick -> {
          scaffoldState.drawerState.close()
          navController.navigate(event.value.route)
        }
      }
    }
  }

  Column(modifier = Modifier.background(MaterialTheme.colors.secondary)) {
    items.forEach { item ->
      when (item) {
        is DrawerItems.ExpandableItem -> ExpandableItem(
          item = item,
          expanded = state.expandedIndex == item.index,
          onClick = { value ->
            viewModel.onEvent(DrawerEvent.OnExpandItemClick(index = value.index))
          },
          onLinkClick = { value ->
            viewModel.onEvent(DrawerEvent.OnLinkItemClick(value = value))
          }
        )
        is DrawerItems.LinkItem -> LinkItem(
          item = item,
          onClick = { value ->
            viewModel.onEvent(DrawerEvent.OnLinkItemClick(value = value))
          }
        )
      }
    }
  }
}

@Preview(
  name = "Drawer",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DrawerPreview() {
  val navController = rememberNavController()
  val scaffoldState = rememberScaffoldState()

  Drawer(
    navController = navController,
    scaffoldState = scaffoldState
  )
}