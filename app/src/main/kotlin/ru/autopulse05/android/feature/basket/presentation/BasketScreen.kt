package ru.autopulse05.android.feature.basket.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.basket.presentation.components.BasketItem
import ru.autopulse05.android.feature.basket.presentation.util.BasketEvent
import ru.autopulse05.android.feature.basket.presentation.util.BasketUiEvent
import ru.autopulse05.android.feature.order.presentation.util.OrderScreens
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun BasketScreen(
  navController: NavController,
  viewModel: BasketViewModel = hiltViewModel()
) {
  val state = viewModel.state
  val scrollState = rememberScrollState()
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is BasketUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
        is BasketUiEvent.NotLoggedIn -> {
          navController.popBackStack()
          navController.navigate(UserScreens.NotSignedIn.route)
        }
        is BasketUiEvent.Checkout -> navController.navigate(
          OrderScreens.Main.withArgs("?positions=${event.positions.toJson()}")
        )
      }
    }
  }

  LoadingScreen(isLoading = state.isLoading) {
    if (state.items.isEmpty()) {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = PresentationText.Resource(R.string.cart_is_empty).asString()
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
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = PresentationText.Resource(R.string.your_cart).asString(),
            style = MaterialTheme.typography.h1
          )

          Checkbox(
            checked = state.selected.size == state.items.size,
            onCheckedChange = { value ->
              viewModel.onEvent(BasketEvent.SelectAllChange(value = value))
            },
            modifier = Modifier.padding(end = 14.dp)
          )
        }

        Spacer(modifier = Modifier.height(SpaceSmall))

        state.items.forEach { item ->
          BasketItem(
            item = item,
            selected = state.selected.contains(item),
            onSelectChange = { value ->
              viewModel.onEvent(BasketEvent.SelectChange(item = item, value = value))
            }
          )

          Spacer(modifier = Modifier.height(SpaceSmall))
        }


      if (state.items.isNotEmpty()) {
        Spacer(modifier = Modifier.height(SpaceNormal))

        BigButton(
          onClick = { viewModel.onEvent(BasketEvent.Checkout) },
          text = PresentationText.Resource(R.string.confirm_order)
        )

        Spacer(modifier = Modifier.height(SpaceSmall))

        BigButton(
          onClick = {
            viewModel.onEvent(event = BasketEvent.Clear)
          },
          text = PresentationText.Dynamic("Удалить выбранное"),
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
  name = "Basket Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BasketScreenPreview() {
  val navController = rememberNavController()

  BasketScreen(navController = navController)
}