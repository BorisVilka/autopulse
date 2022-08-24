package ru.autopulse05.android.feature.vin.presentation.car

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.feature.vin.presentation.car.components.CarParametersSection
import ru.autopulse05.android.feature.vin.presentation.car.util.VinCarEvent
import ru.autopulse05.android.feature.vin.presentation.car.util.VinCarUiEvent
import ru.autopulse05.android.feature.vin.presentation.util.VinScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun VinCarScreen(
  navController: NavController,
  parts: List<String>?,
  car: Car? = null,
  viewModel: VinCarViewModel = hiltViewModel()
) {
  val state = viewModel.state
  val settingsState = viewModel.preferencesState
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(
      VinCarEvent.InitialValuesChange(
        parts = parts!!,
        car = car
      )
    )
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is VinCarUiEvent.GoToGuest -> navController.navigate(
          VinScreens.Guest.withArgs(
            "?parts=${event.parts.toJson()}",
            "&carInfo=${event.carInfo.toJson()}"
          )
        )
        is VinCarUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
        is VinCarUiEvent.GoToStore -> navController.navigate(StoreScreens.Main.route)
      }
    }
  }

  LoadingScreen(isLoading = state.isLoading) {
    Column(
      modifier = Modifier
        .padding(SpaceNormal)
        .fillMaxSize(),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = PresentationText.Dynamic("Подбор запчастей по VIN коду").asString(),
          style = MaterialTheme.typography.h1
        )

        Spacer(modifier = Modifier.height(SpaceSmall))

        Text(
          text = PresentationText.Dynamic("Задайте характеристики автомобиля").asString(),
          style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(SpaceNormal))

        CarParametersSection(
          mode = state.mode,
          onModeChange = { value ->
            viewModel.onEvent(VinCarEvent.ModeChange(value = value))
          },
          vin = state.vin,
          onVinChange = { value ->
            viewModel.onEvent(VinCarEvent.VinChange(value = value))
          },
          frameNumber = state.frameNumber,
          onFrameNumberChange = { value ->
            viewModel.onEvent(VinCarEvent.FrameNumberChange(value = value))
          },
          mark = state.mark,
          onMarkChange = { value ->
            viewModel.onEvent(VinCarEvent.MarkChange(value = value))
          },
          onMarksVisibilityChange = { value ->
            viewModel.onEvent(VinCarEvent.MarksVisibilityChange(value = value))
          },
          year = state.year,
          onYearChange = { value ->
            viewModel.onEvent(VinCarEvent.YearChange(value = value))
          },
          onYearsVisibilityChange = { value ->
            viewModel.onEvent(VinCarEvent.YearsVisibilityChange(value = value))
          },
          model = state.model,
          onModelChange = { value ->
            viewModel.onEvent(VinCarEvent.ModelChange(value = value))
          },
          onModelsVisibilityChange = { value ->
            viewModel.onEvent(VinCarEvent.ModelsVisibilityChange(value = value))
          },
          modification = state.modification,
          onModificationChange = { value ->
            viewModel.onEvent(VinCarEvent.ModificationChange(value = value))
          },
          onModificationsVisibilityChange = { value ->
            viewModel.onEvent(VinCarEvent.ModificationsVisibilityChange(value = value))
          },
          isAdvancedParametersVisible = state.isAdvancedParametersVisible,
          onAdvancedParametersVisibilityChange = { value ->
            viewModel.onEvent(VinCarEvent.AdvancedParametersVisibilityChange(value = value))
          }
        )
      }

      Spacer(modifier = Modifier.height(SpaceNormal))

      if (!settingsState.isLoggedIn) BigButton(
        modifier = Modifier.padding(top = SpaceNormal),
        text = PresentationText.Resource(R.string.continue_word),
        onClick = { viewModel.onEvent(event = VinCarEvent.Submit) },
      ) else BigButton(
        modifier = Modifier.padding(top = SpaceNormal),
        text = PresentationText.Dynamic("Оформить"),
        onClick = { viewModel.onEvent(event = VinCarEvent.Submit) },
      )
    }
  }
}

@Preview(
  name = "Vin Car Screen",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun VinCarScreenPreview() {
  val navController = rememberNavController()

  VinCarScreen(
    navController = navController,
    parts = listOf()
  )
}