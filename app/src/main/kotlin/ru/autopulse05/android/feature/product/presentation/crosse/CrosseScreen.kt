package ru.autopulse05.android.feature.product.presentation.crosse

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.*
import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.presentation.components.CartSection
import ru.autopulse05.android.feature.product.presentation.components.DeliveryPeriod
import ru.autopulse05.android.feature.product.presentation.components.PriceAvailabilitySection
import ru.autopulse05.android.feature.product.presentation.components.ProductImage
import ru.autopulse05.android.feature.product.presentation.crosse.util.ProductCrosseEvent
import ru.autopulse05.android.feature.product.presentation.crosse.util.ProductCrosseUiEvent
import ru.autopulse05.android.feature.product.presentation.detail.util.ProductDetailsEvent
import ru.autopulse05.android.feature.product.presentation.list.components.PieChart
import ru.autopulse05.android.shared.data.remote.HttpRoutes
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection
import ru.autopulse05.android.shared.presentation.util.PresentationText
import kotlin.math.min

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
        is ProductCrosseUiEvent.Toast -> {
          Toast.makeText(
            context,
            event.text,
            Toast.LENGTH_LONG
          ).show()
        }
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
      Spacer(modifier = Modifier.height(SpaceNormal))
      LazyRow {
        items(state.info?.images.orEmpty()) { image ->
          ProductImage(url = HttpRoutes.IMAGES_BASE_URL + image.name)
        }
        if(state.info?.images!!.isEmpty()) {
          items(1) {
            ProductImage(url = HttpRoutes.IMAGES_BASE_URL)
          }
        }
      }

      PriceAvailabilitySection(
        price = state.product?.price.toString(),
        availability = state.product?.availability.toString(),
        modifier = Modifier.padding(SpaceLarge)
      )

      if (state.product?.description.orEmpty().isNotEmpty()) {
        Text(
          text = state.product!!.description,
          style = MaterialTheme.typography.body2,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(bottom = SpaceNormal)
        )
      } else {
        Text(
          text = state.description,
          style = MaterialTheme.typography.body2,
          //modifier = Modifier.padding(SpaceNormal)
        )
      }

      DeliveryPeriod(
        deliveryPeriod = state.product?.deliveryPeriod.toString(),
        deliveryPeriodMax = state.product?.deliveryPeriodMax.orEmpty(),
        deadlineReplace = state.product?.deadlineReplace.orEmpty(),
        supplierColor = state.product?.supplierColor.orEmpty(),
        onClick = {
          viewModel.onEvent(ProductCrosseEvent.DeliveryProbabilityDialogVisibilityChange(value = true))
        }
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
          quantity = min(state.quantity,product!!.availability),
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
      if (state.showDeliveryDialog) {
        Dialog(
          onDismissRequest = { viewModel.onEvent(ProductCrosseEvent.DeliveryProbabilityDialogVisibilityChange(value = false)) }
        ) {
          Card() {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .padding(SpaceNormal)
            ) {
              Text(
                text = PresentationText.Resource(R.string.last_updated)
                  .asString() + " " + product!!.lastUpdateTime
              )

              Spacer(modifier = Modifier.height(SpaceNormal))

              Box {
                PieChart(
                  listOf(
                    product.deliveryProbability,
                    100 - product.deliveryProbability
                  )
                )

                Text(
                  text = product.deliveryProbability.toInt().toString() + "%",
                  modifier = Modifier.align(Alignment.Center)
                )
              }


              Spacer(modifier = Modifier.height(SpaceNormal))

              Spacer(modifier = Modifier.width(SpaceNormal))

              Text(
                text = PresentationText.Resource(R.string.delivery_probability).asString(),
                color = Color.BrandGreen
              )

              Spacer(modifier = Modifier.width(SpaceNormal))

              Text(
                text = PresentationText.Resource(R.string.denie_probability).asString(),
                color = Color.BrandDarkGray
              )

            }
          }

        }
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