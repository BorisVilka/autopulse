package ru.autopulse05.android.feature.vin.presentation.cars

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraSmall
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.car.presentation.components.CarSimpleCard
import ru.autopulse05.android.feature.vin.presentation.cars.util.VinCarsEvent
import ru.autopulse05.android.feature.vin.presentation.cars.util.VinCarsUiEvent
import ru.autopulse05.android.feature.vin.presentation.util.VinScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun VinCarsScreen(
  navController: NavController,
  parts: List<String>?,
  viewModel: VinCarsViewModel = hiltViewModel()
) {
  val context = LocalContext.current
  val state = viewModel.state
  val scrollState = rememberScrollState()

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(VinCarsEvent.InitialValuesChange(parts = parts!!))
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is VinCarsUiEvent.SelectCar -> navController.navigate(
          VinScreens.Car.withArgs("?parts=${event.parts.toJson()}?car=${event.car.toJson()}")
        )
        is VinCarsUiEvent.OtherCar -> navController.navigate(
          VinScreens.Car.withArgs("?parts=${event.parts.toJson()}?car=")
        )
        is VinCarsUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
      }
    }
  }

  LoadingScreen(isLoading = state.isLoading) {
    if (state.cars.isEmpty()) {
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
        text = PresentationText.Dynamic("Подбор запчастей по VIN коду").asString(),
        style = MaterialTheme.typography.h1
      )

      Spacer(modifier = Modifier.height(SpaceSmall))

      Text(
        text = PresentationText.Dynamic("Выберите авто из гаража").asString(),
        style = MaterialTheme.typography.h1
      )

      Spacer(modifier = Modifier.height(SpaceExtraSmall))

      state.cars.forEach { car ->
        CarSimpleCard(
          name = car.name,
          brand = car.modelName,
          onClick = {
            viewModel.onEvent(VinCarsEvent.CarClick(value = car))
          }
        )

        Spacer(modifier = Modifier.height(SpaceSmall))
      }


      if (state.cars.isNotEmpty()) {
        Spacer(modifier = Modifier.height(SpaceNormal))

        BigButton(
          onClick = { viewModel.onEvent(VinCarsEvent.OtherCar) },
          text = PresentationText.Dynamic("Подобрать для другого авто"),
          colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
          ),
          textStyle = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSecondary
          )
        )
      }
    }
  }
}

@Preview(
  name = "Vin Cars Screen",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun VinCarsScreenPreview() {
  val navController = rememberNavController()

  VinCarsScreen(
    navController = navController,
    parts = listOf()
  )
}