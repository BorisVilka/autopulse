package ru.autopulse05.android.feature.vin.presentation.parts

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraSmall
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.vin.presentation.parts.util.VinPartsEvent
import ru.autopulse05.android.feature.vin.presentation.parts.util.VinPartsUiEvent
import ru.autopulse05.android.feature.vin.presentation.util.VinScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.FormTextField
import ru.autopulse05.android.shared.presentation.util.PresentationText


@Composable
fun VinPartsScreen(
  navController: NavController,
  viewModel: VinPartsViewModel = hiltViewModel()
) {
  val state = viewModel.state
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is VinPartsUiEvent.GoToCar -> navController.navigate(
          VinScreens.Car.withArgs("?parts=${event.parts.toJson()}?car=")
        )
        is VinPartsUiEvent.GoToCars -> navController.navigate(
          VinScreens.Cars.withArgs("?parts=${event.parts.toJson()}")
        )
      }
    }
  }

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
        text = PresentationText.Dynamic("Перечислите необходимые запчасти").asString(),
        style = MaterialTheme.typography.subtitle1
      )

      Spacer(modifier = Modifier.height(SpaceExtraSmall))

      FormTextField(
        fieldName = PresentationText.Dynamic("Запчасти"),
        fieldData = state.parts,
        modifier = Modifier.height(120.dp),
        hint = PresentationText.Dynamic("Введите названия необходимых запчастей.\nКаждое с новой строки."),
        onValueChange = { value ->
          viewModel.onEvent(event = VinPartsEvent.PartsChanged(value = value))
        }
      )
    }

    BigButton(
      modifier = Modifier.padding(top = SpaceNormal),
      text = PresentationText.Resource(R.string.continue_word),
      onClick = {
        viewModel.onEvent(event = VinPartsEvent.Next)
      },
    )
  }
}

@Preview(
  name = "Vin Parts Screen",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun VinPartsScreenPreview() {
  val navController = rememberNavController()

  VinPartsScreen(
    navController = navController
  )
}