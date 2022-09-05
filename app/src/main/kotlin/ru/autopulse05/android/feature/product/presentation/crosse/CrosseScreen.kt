package ru.autopulse05.android.feature.product.presentation.crosse

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.presentation.components.CartSection
import ru.autopulse05.android.feature.product.presentation.components.DeliveryPeriod
import ru.autopulse05.android.feature.product.presentation.components.PriceAvailabilitySection
import ru.autopulse05.android.feature.product.presentation.components.ProductImage
import ru.autopulse05.android.feature.product.presentation.crosse.util.ProductCrosseEvent
import ru.autopulse05.android.feature.product.presentation.crosse.util.ProductCrosseUiEvent
import ru.autopulse05.android.shared.data.remote.HttpRoutes
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun CrosseScreen(
  crosse: Crosse?,
  product: Product?,
  viewModel: CrosseViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val preferencesState = viewModel.preferencesState
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(
      ProductCrosseEvent.InitialValuesChange(
        crosse = crosse!!,
        product = product!!
      )
    )
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is ProductCrosseUiEvent.Toast -> Toast.makeText(
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

    state.info
    state.product

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(top = SpaceNormal)
    ) {
      BrandNumberSection(
        brand = state.info?.brand.orEmpty(),
        number = state.info?.number.orEmpty(),
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
        items(state.info?.images.orEmpty()) { image ->
          ProductImage(url = HttpRoutes.IMAGES_BASE_URL + image.name)
        }
      }

      PriceAvailabilitySection(
        price = state.product?.price.toString(),
        availability = state.product?.availability.toString(),
        modifier = Modifier.padding(SpaceLarge)
      )

      DeliveryPeriod(
        deliveryPeriod = state.product?.deliveryPeriod.toString(),
        deliveryPeriodMax = state.product?.deliveryPeriodMax.orEmpty(),
        deadlineReplace = state.product?.deadlineReplace.orEmpty(),
        supplierColor = state.product?.supplierColor.orEmpty()
      )

      Text(
        text = PresentationText.Resource(R.string.update)
          .asString() + " " + state.product?.lastUpdateTime,
        modifier = Modifier.padding(top = SpaceNormal)
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(SpaceLarge)
      ) {
        Button(
          onClick = {},
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
            viewModel.onEvent(ProductCrosseEvent.AddToBasket)
          },
          onIncreaseProductCountClick = {
            viewModel.onEvent(ProductCrosseEvent.IncreaseQuantityToAdd)
          },
          onDecreaseProductCountClick = {
            viewModel.onEvent(ProductCrosseEvent.DecreaseQuantityToAdd)
          }
        )
      }
    }
  }
}

@Preview(
  name = "Crosse Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CrosseScreenPreview() {

  CrosseScreen(
    crosse = Crosse(
      brand = "brand",
      number = "number"
    ),
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