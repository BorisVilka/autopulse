package ru.autopulse05.android.feature.laximo.presentation.categories

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.laximo.data.remote.dto.LaximoQuickGroupDto
import ru.autopulse05.android.feature.laximo.presentation.categories.components.CategoryItem
import ru.autopulse05.android.feature.laximo.presentation.categories.util.LaximoCategoriesEvent
import ru.autopulse05.android.feature.laximo.presentation.categories.util.LaximoCategoriesUiEvent
import ru.autopulse05.android.feature.laximo.presentation.util.LaximoScreens
import ru.autopulse05.android.feature.order.presentation.components.PositionItem
import ru.autopulse05.android.feature.order.presentation.util.OrderEvent
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.ExpandableCard
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun LaximoCategoriesScreen(
  navController: NavController,
  catalog: String?,
  vehicleId: String?,
  ssd: String?,
  viewModel: LaximoCategoriesViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(
      LaximoCategoriesEvent.InitialValuesChange(
        catalog = catalog!!,
        vehicleId = vehicleId!!,
        ssd = ssd!!
      )
    )
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is LaximoCategoriesUiEvent.BasicCategoryClick -> navController.navigate(
          LaximoScreens.Units.withArgs(
            "?catalog=${event.catalog}",
            "&vehicleId=${event.vehicleId}",
            "&categoryId=${event.value.id}",
            "&ssd=${event.value.ssd}"
          )
        )
        is LaximoCategoriesUiEvent.Toast -> Toast.makeText(
          context,
          event.text,
          Toast.LENGTH_LONG
        ).show()
      }
    }
  }

  LoadingScreen(isLoading = state.isLoading) {
    Column(
      modifier = Modifier
        .padding(SpaceNormal)
     ) {
      Text(
        text = PresentationText.Dynamic("Категории").asString(),
        style = MaterialTheme.typography.h1
      )

      Spacer(modifier = Modifier.height(SpaceSmall))

      LazyColumn(modifier =  Modifier
        .background(color = MaterialTheme.colors.secondary, shape = MaterialTheme.shapes.small)
        .padding(vertical = SpaceSmall)
        .fillMaxWidth()) {
        items(state.quickGroup) {
          getUnits(list = it,
            viewModel = viewModel
          ) {
            navController.navigate(
              LaximoScreens.Units.withArgs(
                "?catalog=${state.catalog}",
                "&vehicleId=${state.vehicleId}",
                "&categoryId=${it.quickgroupid}",
                "&ssd=${state.ssd}"
              )
            )
          }
        }
      }
    }
  }
}

@Composable
fun getUnits(viewModel: LaximoCategoriesViewModel, list: LaximoQuickGroupDto, v: (LaximoQuickGroupDto) -> Unit) {
  if(list.childs.isEmpty()) {
    Text(text = list.name, modifier = Modifier
      .fillMaxWidth()
      .padding(SpaceSmall)
      .clickable {
        v(list);
      }
    )
  } else {
    ExpandableCard(
      title = PresentationText.Dynamic(list.name),
      modifier = Modifier.padding(SpaceSmall),
      onClick = {
        viewModel.state = viewModel.state.copy(
          vis = viewModel.state.vis.put(list.quickgroupid,!viewModel.state.vis[list.quickgroupid]?.or(false)!!)
        )
      },
      expanded = viewModel.state.vis[list.quickgroupid]?.or(false)!!,
      shape = MaterialTheme.shapes.small,
      contentExpandedBackgroundColor = Color.BrandYellow.copy(alpha = 0.5f),
      contentCollapsedBackgroundColor = Color.BrandYellow.copy(alpha = 0.5f)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colors.secondary)
          .padding(SpaceSmall)
      ) {
        list.childs.forEach {
            getUnits(list = it,v = v, viewModel = viewModel)
        }
      }
    }
  }
}

@Preview(
  name = "Laximo Categories Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LaximoCategoriesScreenPreview() {
  val navController = rememberNavController()

  LaximoCategoriesScreen(
    navController = navController,
    catalog = "catalog",
    vehicleId = "vehicle id",
    ssd = "ssd"
  )
}