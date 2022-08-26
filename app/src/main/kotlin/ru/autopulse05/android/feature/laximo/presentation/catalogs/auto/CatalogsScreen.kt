package ru.autopulse05.android.feature.laximo.presentation.catalogs.auto

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.laximo.presentation.catalogs.components.SearchByVinOrFrameSection
import ru.autopulse05.android.feature.laximo.presentation.catalogs.util.CatalogsEvent
import ru.autopulse05.android.feature.laximo.presentation.catalogs.util.CatalogsUiEvent
import ru.autopulse05.android.feature.laximo.presentation.util.LaximoScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun AutoCatalogsScreen(
  navController: NavController,
  viewModel: CatalogsViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is CatalogsUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
        is CatalogsUiEvent.GoToVehicleSearch -> navController.navigate(
          LaximoScreens.VehicleSearch.withArgs("?catalog=${event.value.code}")
        )
        is CatalogsUiEvent.GoToVehicles -> navController.navigate(
          LaximoScreens.Vehicles.withArgs("?vehicles=${event.vehicles.toJson()}")
        )
      }
    }
  }

  LoadingScreen(
    isLoading = state.isLoading
  ) {
    LazyVerticalGrid(
      contentPadding = PaddingValues(SpaceNormal),
      columns = GridCells.Fixed(2),
      modifier = Modifier.fillMaxWidth()
    ) {
      item(
        span = { GridItemSpan(2) }
      ) {
        Text(
          text = PresentationText.Resource(R.string.catalogs).asString(),
          style = MaterialTheme.typography.h1,
          modifier = Modifier.padding(top = SpaceNormal, bottom = SpaceNormal)
        )
      }
      items(items = state.items) {
        Text(
          text = it.name,
          style = MaterialTheme.typography.body2,
          modifier = Modifier
            .padding(bottom = SpaceSmall)
            .clickable {
              viewModel.onEvent(CatalogsEvent.OnCatalogClick(it))
            }
        )
      }
    }
  }
}
