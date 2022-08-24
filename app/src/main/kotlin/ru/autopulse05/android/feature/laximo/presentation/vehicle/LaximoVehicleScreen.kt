package ru.autopulse05.android.feature.laximo.presentation.vehicle

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import ru.autopulse05.android.feature.laximo.presentation.util.LaximoScreens
import ru.autopulse05.android.feature.laximo.presentation.vehicle.util.LaximoVehicleEvent
import ru.autopulse05.android.feature.laximo.presentation.vehicle.util.LaximoVehicleUiEvent
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.FormDropdownField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun LaximoVehicleScreen(
  navController: NavController,
  catalog: String?,
  viewModel: LaximoVehicleViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(LaximoVehicleEvent.InitialValuesChange(catalog = catalog!!))
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is LaximoVehicleUiEvent.Toast -> Toast.makeText(
          context,
          event.text,
          Toast.LENGTH_LONG
        ).show()
        is LaximoVehicleUiEvent.GoToVehicles -> navController.navigate(
          LaximoScreens.Vehicles.withArgs(
            "?catalog=${state.catalog}",
            "&vehicles=${event.vehicles.toJson()}"
          )
        )
      }
    }
  }
  LoadingScreen(isLoading = state.isLoading) {
    Column(
      modifier = Modifier
        .padding(SpaceNormal)
        .verticalScroll(scrollState)
    ) {
      Text(
        text = PresentationText.Dynamic("Поиск автомобиля по параметрам").asString(),
        style = MaterialTheme.typography.h1
      )

      Spacer(modifier = Modifier.height(SpaceSmall))

      state.fields.forEachIndexed { index, (name, field) ->
        FormDropdownField(
          fieldName = PresentationText.Dynamic(name),
          items = field.items.map { it.value },
          selectedName = field.value?.value,
          isShowing = field.isShowing,
          onMenuClick = {
            viewModel.onEvent(
              LaximoVehicleEvent.FieldItemsVisibilityChange(
                value = true,
                index = index
              )
            )
          },
          isDisabled = field.isDisabled,
          onItemClick = {
            viewModel.onEvent(
              LaximoVehicleEvent.FieldValueChange(
                value = field.items[it],
                index = index
              )
            )
          },
          onDismissRequest = {
            viewModel.onEvent(
              LaximoVehicleEvent.FieldItemsVisibilityChange(
                value = false,
                index = index
              )
            )
          },
          hint = PresentationText.Dynamic(name)
        )
      }

      Spacer(modifier = Modifier.height(SpaceNormal))

      BigButton(
        onClick = { viewModel.onEvent(LaximoVehicleEvent.Submit) },
        text = PresentationText.Resource(R.string.search),
        isDisabled = state.fields.firstOrNull { (_, field) -> field.value != null } == null
      )
    }
  }
}

@Preview(
  name = "Laximo Vehicle Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LaximoVehicleScreenPreview() {
  val navController = rememberNavController()

  LaximoVehicleScreen(
    navController = navController,
    catalog = "catalog"
  )
}