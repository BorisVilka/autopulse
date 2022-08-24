package ru.autopulse05.android.feature.laximo.presentation.unit

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.glide.GlideImage
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.laximo.presentation.unit.components.DetailItem
import ru.autopulse05.android.feature.laximo.presentation.unit.util.LaximoUnitEvent
import ru.autopulse05.android.feature.laximo.presentation.unit.util.LaximoUnitUiEvent
import ru.autopulse05.android.feature.search.presentation.util.SearchScreens
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun LaximoUnitScreen(
  navController: NavController,
  catalog: String?,
  unitId: String?,
  ssd: String?,
  viewModel: LaximoUnitViewModel = hiltViewModel()
) {
  val state = viewModel.state
  val context = LocalContext.current
  val scrollState = rememberScrollState()
  val imageWidth = 250

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(
      LaximoUnitEvent.InitialValuesChange(
        catalog = catalog!!,
        unitId = unitId!!,
        ssd = ssd!!
      )
    )
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is LaximoUnitUiEvent.OnDetailClicked -> {
          val oem = event.value.oem
          if (oem != null) navController.navigate(SearchScreens.Main.withArgs("?number=${oem}"))
        }
        is LaximoUnitUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
      }
    }
  }

  LoadingScreen(isLoading = state.isLoading) {
    Column(
      modifier = Modifier
        .padding(SpaceNormal)
        .fillMaxWidth()
        .verticalScroll(scrollState)
    ) {
      Text(
        text = PresentationText.Dynamic("Узел ${state.unit!!.name}").asString(),
        style = MaterialTheme.typography.h1
      )

      Spacer(modifier = Modifier.height(SpaceSmall))

      GlideImage(
        imageModel = state.unit.imageUrl.replace("%size%", "source").replace("amb;",""),
        contentDescription = PresentationText.Resource(R.string.unit_photo).asString(),
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        contentScale = ContentScale.None
      )

      Spacer(modifier = Modifier.height(SpaceSmall))

      Text(
        text = PresentationText.Dynamic("Детали").asString(),
        style = MaterialTheme.typography.subtitle1
      )

      Spacer(modifier = Modifier.height(SpaceSmall))

      state.details.forEach { detail ->
        DetailItem(
          detail = detail,
          onClick = {
            viewModel.onEvent(LaximoUnitEvent.DetailClick(value = detail))
          }
        )

        Spacer(modifier = Modifier.height(SpaceSmall))
      }
    }
  }
}

@Preview(
  name = "Laximo Unit Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LaximoUnitsScreenPreview() {
  val navController = rememberNavController()

  LaximoUnitScreen(
    navController = navController,
    catalog = "catalog",
    unitId = "id",
    ssd = "ssd"
  )
}