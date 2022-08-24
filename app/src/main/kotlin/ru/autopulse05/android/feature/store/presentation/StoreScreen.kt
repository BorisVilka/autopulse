package ru.autopulse05.android.feature.store.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.store.presentation.components.Menu
import ru.autopulse05.android.feature.store.presentation.components.SupportService
import ru.autopulse05.android.feature.store.presentation.util.MenuItems
import ru.autopulse05.android.feature.store.presentation.util.StoreEvent
import ru.autopulse05.android.feature.store.presentation.util.StoreUiEvent
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun StoreScreen(
  navController: NavController,
  viewModel: StoreViewModel = hiltViewModel()
) {

  val scrollState = rememberScrollState()
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is StoreUiEvent.MenuItemClick -> navController.navigate(event.value.route)
      }
    }
  }

  val menuItems = listOf(
    MenuItems.OriginalCatalogue,
    MenuItems.Catalogue,
    MenuItems.RequestByVin
  )

  Column(
    modifier = Modifier.verticalScroll(scrollState)
  ) {
    Image(
      painter = painterResource(id = R.drawable.img_logo),
      contentDescription = null,
      modifier = Modifier
        .shadow(elevation = SpaceSmall)
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.surface)
        .padding(SpaceNormal)
    )

    Spacer(modifier = Modifier.height(SpaceLarge))

    Menu(
      title = PresentationText.Resource(R.string.spare_parts),
      items = menuItems,
      onItemClick = { item ->
        viewModel.onEvent(StoreEvent.MenuItemClick(value = item))
      },
      modifier = Modifier
        .shadow(elevation = SpaceSmall)
        .background(color = MaterialTheme.colors.surface)
        .padding(top = SpaceNormal)
    )

    Spacer(modifier = Modifier.height(SpaceLarge))

    SupportService(
      modifier = Modifier
        .shadow(elevation = SpaceSmall)
        .background(color = MaterialTheme.colors.surface)
        .padding(SpaceNormal),
      onCallClick = {
        viewModel.onEvent(StoreEvent.CallSupport)
      },
      onWriteClick = {
        viewModel.onEvent(StoreEvent.WriteToSupport)
      }
    )
  }
}

@Preview(
  name = "Store Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun StoreScreenPreview() {
  val navController = rememberNavController()

  StoreScreen(
    navController = navController
  )
}