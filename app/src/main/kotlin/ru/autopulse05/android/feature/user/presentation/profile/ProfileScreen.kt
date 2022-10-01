package ru.autopulse05.android.feature.user.presentation.profile


import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.car.presentation.util.CarScreens
import ru.autopulse05.android.feature.garage.presentation.util.GarageScreens
import ru.autopulse05.android.feature.laximo.presentation.util.LaximoScreens
import ru.autopulse05.android.feature.order.presentation.util.OrderScreens
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.feature.user.presentation.profile.components.OrderItem
import ru.autopulse05.android.feature.user.presentation.profile.components.ProfileTabLayout
import ru.autopulse05.android.feature.user.presentation.profile.components.ProfileTabPage
import ru.autopulse05.android.feature.user.presentation.profile.util.*
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.feature.vin.presentation.util.VinScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ProfileScreen(
  tabIndex: Int?,
  navController: NavController,
  profileTabState: ProfileTabState,
  viewModel: ProfileViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val scrollState = rememberScrollState()
  val context = LocalContext.current
  val tabs = listOf(
    ProfileTabs.Orders,
    ProfileTabs.Payments,
    ProfileTabs.Garage,
    ProfileTabs.Data
  )

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(ProfileEvent.TabChange(value = tabs.first { it.index == tabIndex }))
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is ProfileUiEvent.SignOut -> navController.navigate(StoreScreens.Main.route)
        is ProfileUiEvent.EditCar -> navController.navigate(
          CarScreens.Edit.withArgs("?car=${event.value.toJson()}")
        )
        is ProfileUiEvent.OrderDetail -> navController.navigate(
          OrderScreens.Detail.withArgs("?order=${event.value.toJson()}")
        )
        is ProfileUiEvent.AddCar -> navController.navigate(
          GarageScreens.Add.route
        )
        is ProfileUiEvent.NotSignedIn -> {
          navController.popBackStack()
          navController.navigate(UserScreens.NotSignedIn.route)
        }
        is ProfileUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
        is ProfileUiEvent.TabChange -> profileTabState.change(event.value)
        is ProfileUiEvent.OpenVinRequest -> navController.navigate(
          VinScreens.Parts.route
        )
        is ProfileUiEvent.GoToVehicles -> navController.navigate(
          LaximoScreens.Vehicles.withArgs("?vehicles=${event.vehicles.toJson()}")
        )
      }
    }
  }

  LoadingScreen(isLoading = state.isLoading) {
    if (state.isNotFound) Box(
      modifier = Modifier.fillMaxSize()
        .background(color = MaterialTheme.colors.surface),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = PresentationText.Dynamic("Информация не найдена").asString()
      )
    } else {
      Column {
        state.orders.forEach { order ->
          OrderItem(item = order)
        }
      }
    }

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .background(color = MaterialTheme.colors.surface)
        .padding(SpaceNormal)
    ) {
      ProfileTabLayout(
        profileTabState = profileTabState,
        onClick = { value ->
          viewModel.onEvent(ProfileEvent.TabChange(value = value))
        }
      )

      if (!state.isNotFound) {
        ProfileTabPage(
          tabs = tabs,
          profileTabState = profileTabState
        )
      }
    }
  }
}

@Preview(
  name = "Profile Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileScreenPreview() {
  val navController = rememberNavController()
  val profileTabState = rememberProfileTabState()

  ProfileScreen(
    navController = navController,
    tabIndex = ProfileTabs.Data.index,
    profileTabState = profileTabState
  )
}