package ru.autopulse05.android.feature.product.presentation.detail

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.*
import ru.autopulse05.android.feature.laximo.presentation.catalogs.util.CatalogsEvent
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.presentation.components.CartSection
import ru.autopulse05.android.feature.product.presentation.detail.components.CrosseItem
import ru.autopulse05.android.feature.product.presentation.components.DeliveryPeriod
import ru.autopulse05.android.feature.product.presentation.components.PriceAvailabilitySection
import ru.autopulse05.android.feature.product.presentation.components.ProductImage
import ru.autopulse05.android.feature.product.presentation.detail.util.ProductDetailsEvent
import ru.autopulse05.android.feature.product.presentation.detail.util.ProductDetailsUiEvent
import ru.autopulse05.android.feature.product.presentation.list.components.PieChart
import ru.autopulse05.android.feature.product.presentation.util.ProductScreens
import ru.autopulse05.android.feature.search.presentation.util.SearchScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.data.remote.HttpRoutes
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection
import ru.autopulse05.android.shared.presentation.components.SecondaryButton
import ru.autopulse05.android.shared.presentation.util.PresentationText
import kotlin.math.min

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
      Spacer(modifier = Modifier.height(SpaceNormal))
      LazyRow {
        items(state.info.images) { image ->
          ProductImage(url = HttpRoutes.IMAGES_BASE_URL + image.name)
        }
        if(state.info.images.isEmpty()) {
          items(1) {
            ProductImage(url = HttpRoutes.IMAGES_BASE_URL)
          }
        }
      }

      PriceAvailabilitySection(
        price = state.product.price.toString(),
        availability = state.product.availability.toString(),
        modifier = Modifier.padding(SpaceLarge)
      )

      if (state.product.description.isNotEmpty()) {
        Text(
          text = state.product.description,
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
        deliveryPeriod = state.product.deliveryPeriod.toString(),
        deliveryPeriodMax = state.product.deliveryPeriodMax,
        deadlineReplace = state.product.deadlineReplace,
        supplierColor = state.product.supplierColor,
        onClick = {
          viewModel.onEvent(ProductDetailsEvent.DeliveryProbabilityDialogVisibilityChange(value = true))
        }
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
          quantity = min(state.quantity,product!!.availability),
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
    if (state.showDeliveryDialog) {
      Dialog(
        onDismissRequest = { viewModel.onEvent(ProductDetailsEvent.DeliveryProbabilityDialogVisibilityChange(value = false)) }
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
    if(state.showApplication) {
      Dialog(onDismissRequest = {
        viewModel.state = viewModel.state.copy(
          showApplication = false
        )
      }, 
      properties = DialogProperties()
        ) {
       Column(modifier = Modifier
          .fillMaxSize()
         .background(MaterialTheme.colors.background)
       ) {
         Text(
           text = "Применимость",
           style = MaterialTheme.typography.subtitle1,
           fontSize = 24.sp,
           modifier = Modifier.padding(SpaceNormal)
         )

         LazyRow {
           items(state.applications.keys.toList()) {

             SecondaryButton(
               text = PresentationText.Dynamic(it),
               modifier = Modifier.weight(0.33f).padding(SpaceSmall),
               colors = ButtonDefaults.buttonColors(
                 backgroundColor = if (state.choicedBrand == it) {
                   MaterialTheme.colors.background
                 } else MaterialTheme.colors.surface
               ),
               onClick = {
                 viewModel.state = viewModel.state.copy(
                   choicedBrand = it
                 )
               }
             )
           }
         }
         LazyRow {
           items(state.applications[state.choicedBrand].orEmpty().toList()) {
             SecondaryButton(
               text = PresentationText.Dynamic(it.first),
               modifier = Modifier.weight(0.33f).padding(SpaceSmall),
               colors = ButtonDefaults.buttonColors(
                 backgroundColor = if (state.choicedModel == it.first) {
                   MaterialTheme.colors.background
                 } else MaterialTheme.colors.surface
               ),
               onClick = {
                 viewModel.state = viewModel.state.copy(
                   choicedModel = it.first,
                   list = it.second
                 )
               }
             )
           }
         }

         LazyColumn(modifier = Modifier
           .fillMaxSize()
           .horizontalScroll(rememberScrollState())
         ) {
           items(state.list) {

             Row(
               modifier = Modifier
                 .fillMaxSize()
                 .padding(SpaceSmall)
             ) {

               Text(
                 text = "Модель:\n${it.model}",
                 style = MaterialTheme.typography.subtitle1
               )
               Spacer(modifier = Modifier.width(SpaceNormal))
               Text(
                 text = "Описание:\n${it.desc}",
                 style = MaterialTheme.typography.subtitle1
               )
               Divider()
               Spacer(modifier = Modifier.width(SpaceNormal))
               Text(
                 text = "Период:\n${it.period}",
                 style = MaterialTheme.typography.subtitle1
               )
               Divider()
               Spacer(modifier = Modifier.width(SpaceNormal))
               Text(
                 text = "Опции:\n${it.options}",
                 style = MaterialTheme.typography.subtitle1
               )
             }
             Divider(color = MaterialTheme.colors.onSecondary, thickness = 1.dp)
           }
         }
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