package ru.autopulse05.android.feature.laximo.presentation.units

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.laximo.presentation.units.components.UnitItem
import ru.autopulse05.android.feature.laximo.presentation.units.util.LaximoUnitsEvent
import ru.autopulse05.android.feature.laximo.presentation.units.util.LaximoUnitsUiEvent
import ru.autopulse05.android.feature.laximo.presentation.util.LaximoScreens
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun LaximoUnitsScreen(
  navController: NavController,
  catalog: String?,
  categoryId: String?,
  ssd: String?,
  vehicleId: String?,
  viewModel: LaximoUnitsViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(
      LaximoUnitsEvent.OnInitialValuesChanged(
        catalog = catalog!!,
        categoryId = categoryId!!,
        ssd = ssd!!,
        vehicleId = vehicleId!!
      )
    )
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is LaximoUnitsUiEvent.OnUnitClicked -> navController.navigate(
          LaximoScreens.Unit.withArgs(
            "?catalog=${event.catalog}",
            "&unitId=${event.value.id}",
            "&ssd=${event.value.ssd}"
          )
        )
        is LaximoUnitsUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
      }
    }
  }

  LoadingScreen(isLoading = state.isLoading) {
    Column(
      modifier = Modifier
        .padding(SpaceNormal)
      ) {
      Text(
        text = PresentationText.Dynamic("Узлы").asString(),
        style = MaterialTheme.typography.h1
      )

      Spacer(modifier = Modifier.height(SpaceSmall))

      LazyColumn() {
        items(state.units) { unit ->
          UnitItem(
            unit = unit,
            onClick = {
              viewModel.onEvent(LaximoUnitsEvent.OnUnitClicked(value = unit))
            }
          )

          Spacer(modifier = Modifier.height(SpaceSmall))
        }
      }
    }
  }
}

@Preview(
  name = "Laximo Units Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LaximoUnitsScreenPreview() {
  val navController = rememberNavController()

  LaximoUnitsScreen(
    navController = navController,
    catalog = "catalog",
    ssd = "ssd",
    vehicleId = "vehicle id",
    categoryId = "categoryId id"
  )
}