package ru.autopulse05.android.feature.search.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.product.presentation.util.ProductScreens
import ru.autopulse05.android.feature.search.presentation.components.HistoryItemList
import ru.autopulse05.android.feature.search.presentation.components.SearchBar
import ru.autopulse05.android.feature.search.presentation.components.TipList
import ru.autopulse05.android.feature.search.presentation.util.SearchEvent
import ru.autopulse05.android.feature.search.presentation.util.SearchUiEvent
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun SearchScreen(
  navController: NavController,
  number: String? = null,
  viewModel: SearchViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val context = LocalContext.current
  val focusRequester = remember { FocusRequester() }

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(SearchEvent.InitialValuesChange(number = number!!))
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is SearchUiEvent.GoToList -> navController.navigate(
          ProductScreens.List.withArgs(
            "?brand=${event.brand}",
            "&number=${event.number}",
          )
        )
        is SearchUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
      }
    }
  }

  DisposableEffect(key1 = context) {
    focusRequester.requestFocus()
    onDispose { }
  }

  Column {
    SearchBar(
      value = state.number,
      onValueChange = { value ->
        viewModel.onEvent(SearchEvent.NumberChange(value = value))
      },
      hint = PresentationText.Resource(R.string.detail_number).asString(),
      modifier = Modifier
        .focusRequester(focusRequester)
        .padding(SpaceNormal)
    )

    if (state.showHistory) HistoryItemList(
      historyItems = state.historyItems,
      onClick = { value ->
        viewModel.onEvent(SearchEvent.HistoryItemClick(value = value))
      }
    ) else TipList(
        searchTips = state.tips,
        didRequest = state.didRequest,
        onClick = { value ->
          viewModel.onEvent(SearchEvent.TipClick(value = value))
        }
      )


  }
}

@Preview(
  name = "Search Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SearchScreenPreview() {
  val navController = rememberNavController()

  SearchScreen(navController = navController, number = "")
}