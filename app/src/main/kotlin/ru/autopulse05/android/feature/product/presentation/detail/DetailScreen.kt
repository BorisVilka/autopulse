package ru.autopulse05.android.feature.product.presentation.detail

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.presentation.components.CartSection
import ru.autopulse05.android.feature.product.presentation.detail.components.CrosseItem
import ru.autopulse05.android.feature.product.presentation.components.DeliveryPeriod
import ru.autopulse05.android.feature.product.presentation.components.PriceAvailabilitySection
import ru.autopulse05.android.feature.product.presentation.components.ProductImage
import ru.autopulse05.android.feature.product.presentation.detail.util.ProductDetailsEvent
import ru.autopulse05.android.feature.product.presentation.detail.util.ProductDetailsUiEvent
import ru.autopulse05.android.feature.product.presentation.util.ProductScreens
import ru.autopulse05.android.feature.search.presentation.util.SearchScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.data.remote.HttpRoutes
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun DetailScreen(
  navController: NavController,
  product: Product?,
  viewModel: DetailViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val preferencesState = viewModel.preferencesState
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(
      ProductDetailsEvent.InitialValuesChange(
        product = product!!
      )
    )
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is ProductDetailsUiEvent.GoToCrosse -> navController.navigate(
          ProductScreens.Crosse.withArgs(
            "?crosse=${event.crosse.toJson()}",
            "&product=${event.product.toJson()}"
          )
        ) {
          popUpTo(SearchScreens.Main.route)
        }
        is ProductDetailsUiEvent.Toast -> Toast.makeText(
          context,
          event.text,
          Toast.LENGTH_LONG
        ).show()
      }
    }
  }

  LoadingScreen(
    isLoading = state.isLoading
  ) {
    state.info!!
    state.product!!

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(top = SpaceNormal)
    ) {
      BrandNumberSection(
        brand = state.info.brand,
        number = state.info.number,
        modifier = Modifier
          .background(color = MaterialTheme.colors.surface)
          .padding(SpaceSmall)
      )

      if (state.description.isNotEmpty()) {
        Text(
          text = state.description,
          style = MaterialTheme.typography.body2,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(SpaceNormal)
        )
      } else {
        Text(
          text = state.description,
          style = MaterialTheme.typography.body2,
          modifier = Modifier.padding(SpaceNormal)
        )
      }

      LazyRow {
        items(state.info.images) { image ->
          ProductImage(url = HttpRoutes.IMAGES_BASE_URL + image.name)
        }
      }

      PriceAvailabilitySection(
        price = state.product.price.toString(),
        availability = state.product.availability.toString(),
        modifier = Modifier.padding(SpaceLarge)
      )

      DeliveryPeriod(
        deliveryPeriod = state.product.deliveryPeriod.toString(),
        deliveryPeriodMax = state.product.deliveryPeriodMax,
        deadlineReplace = state.product.deadlineReplace,
        supplierColor = state.product.supplierColor
      )

      Text(
        text = PresentationText.Resource(R.string.update)
          .asString() + " " + state.product.lastUpdateTime,
        modifier = Modifier.padding(top = SpaceNormal)
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(SpaceLarge)
      ) {
        Button(
          onClick = {
                    viewModel.getApplicationLaximo()
          },
          colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
        ) {
          Text(
            text = PresentationText.Resource(R.string.applicability_short).asString(),
            color = MaterialTheme.colors.onSecondary
          )
        }

        Spacer(modifier = Modifier.width(SpaceSmall))

        if (preferencesState.isLoggedIn) CartSection(
          quantity = state.quantity,
          onAddToCartClick = {
            viewModel.onEvent(ProductDetailsEvent.AddToBasket)
          },
          onIncreaseProductCountClick = {
            viewModel.onEvent(ProductDetailsEvent.IncreaseQuantityToAdd)
          },
          onDecreaseProductCountClick = {
            viewModel.onEvent(ProductDetailsEvent.DecreaseQuantityToAdd)
          }
        )
      }

      Text(
        text = PresentationText.Resource(R.string.crosses).asString(),
        style = MaterialTheme.typography.subtitle2,
      )

      Column() {
        state.info.crosses.forEach { crosse ->
          CrosseItem(
            brand = crosse.brand,
            number = crosse.number,
            onClick = {
              viewModel.onEvent(ProductDetailsEvent.OpenCrosseDetails(value = crosse))
            }
          )
          Divider()
        }
      }
    }
  }
}

@Preview(
  name = "Detail Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DetailScreenPreview() {
  val navController = rememberNavController()

  DetailScreen(
    navController = navController,
    product = Product(
      brand = "brand",
      number = "number",
      numberFix = "numberFix",
      description = "description",
      availability = 1,
      packing = 1,
      deliveryPeriod = 1,
      deliveryPeriodMax = "deliveryPeriodMax",
      deadlineReplace = "deadlineReplace",
      distributorCode = "distributorCode",
      supplierCode = "supplierCode",
      supplierColor = "supplierColor",
//      supplierDescription = "supplierDescription",
      itemKey = "itemKey",
      price = 1.0,
      weight = 1.0,
      deliveryProbability = 1f,
      lastUpdateTime = "lastUpdateTime",
      additionalPrice = 1,
      noReturn = false,
      distributorId = 1,
      code = "code"
    )
  )
}