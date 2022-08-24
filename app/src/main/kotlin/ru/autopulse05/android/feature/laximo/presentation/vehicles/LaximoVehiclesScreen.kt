package ru.autopulse05.android.feature.laximo.presentation.vehicles

import android.content.res.Configuration
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
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.car.presentation.components.CarSimpleCard
import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle
import ru.autopulse05.android.feature.laximo.presentation.util.LaximoScreens
import ru.autopulse05.android.feature.laximo.presentation.vehicles.util.LaximoVehiclesEvent
import ru.autopulse05.android.feature.laximo.presentation.vehicles.util.LaximoVehiclesUiEvent
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun LaximoVehiclesScreen(
  navController: NavController,
  vehicles: List<LaximoVehicle>?,
  viewModel: LaximoVehiclesViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(
      LaximoVehiclesEvent.OnInitialValuesChanged(
        vehicles = vehicles!!
      )
    )
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is LaximoVehiclesUiEvent.GoToCategories -> navController.navigate(
          LaximoScreens.Categories.withArgs(
            "?catalog=${event.value.catalog}",
            "&ssd=${event.value.ssd}",
            "&vehicleId=${event.value.id}"
          )
        )
      }
    }
  }

  if (state.vehicles.isEmpty()) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = PresentationText.Dynamic("Автомобилей нет").asString()
      )
    }
  }

  Column(
    modifier = Modifier
      .padding(SpaceNormal)
      .fillMaxWidth()
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = PresentationText.Dynamic("Список автомобилей").asString(),
      style = MaterialTheme.typography.h1
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    state.vehicles.forEach { vehicle ->
      CarSimpleCard(
        name = vehicle.name,
        brand = vehicle.brand,
        onClick = {
          viewModel.onEvent(LaximoVehiclesEvent.OnVehicleClicked(value = vehicle))
        }
      )

      Spacer(modifier = Modifier.height(SpaceSmall))
    }
  }
}

@Preview(
  name = "Laximo Vehicles Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LaximoVehiclesScreenPreview() {
  val navController = rememberNavController()

  LaximoVehiclesScreen(
    navController = navController,
    vehicles = listOf()
  )
}