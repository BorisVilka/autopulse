package ru.autopulse05.android.feature.product.presentation.list

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.topbar.TopBar
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.feature.cart.presentation.util.CartScreens
import ru.autopulse05.android.feature.product.domain.util.OrderType
import ru.autopulse05.android.feature.product.presentation.list.components.AvailabilityFilterSection
import ru.autopulse05.android.feature.product.presentation.list.components.DetailItem
import ru.autopulse05.android.feature.product.presentation.list.components.PriceFilterSection
import ru.autopulse05.android.feature.product.presentation.list.util.ProductListEvent
import ru.autopulse05.android.feature.product.presentation.list.util.ProductListUiEvent
import ru.autopulse05.android.feature.product.presentation.util.ProductScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.popup.PopupState
import ru.autopulse05.android.shared.presentation.popup.rememberPopupState
import ru.autopulse05.android.shared.presentation.util.PresentationText

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductListScreen(
  navController: NavController,
  scaffoldState: ScaffoldState,
  brand: String?,
  number: String?,
  popupState: PopupState,
  viewModel: ProductListViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val scrollState = rememberScrollState()
  val settingsState = viewModel.preferencesState
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
        when (event) {
            is ProductListUiEvent.GoToProductDetails -> navController.navigate(
                ProductScreens.Detail.withArgs("?product=${event.value.toJson()}")
            )
            is ProductListUiEvent.GoToBasket -> navController.navigate(
                CartScreens.Main.route
            )
            is ProductListUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG)
                .show()
        }
    }
  }

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(ProductListEvent.InitialValuesChange(brand = brand!!, number = number!!))
  }

  Scaffold(
    topBar = {
      TopBar(
        scaffoldState = scaffoldState,
        navController = navController,
        popupState = popupState,
        actions = {
          IconButton(
            onClick = {
              viewModel.onEvent(
                ProductListEvent.PriceFilterVisibilityChange(
                  value = !state.priceFilterIsShowing
                )
              )
            }
          ) {
            Icon(
              painter = painterResource(id = R.drawable.ic_dollar),
              contentDescription = PresentationText.Resource(R.string.filter_by_price).asString(),
              tint = Color.BrandYellow
            )
          }
          IconButton(
            onClick = {
              viewModel.onEvent(
                ProductListEvent.AvailabilityFilterVisibilityChange(
                  value = !state.priceFilterIsShowing
                )
              )
            }
          ) {
            Icon(
              painter = painterResource(id = R.drawable.ic_box),
              contentDescription = PresentationText.Resource(
                R.string.filter_by_availability
              ).asString(),
              tint = Color.BrandYellow
            )
          }
        }
      )
    }
  ) {
    LoadingScreen(
      isLoading = state.isLoading
    ) {
      if (state.products.isEmpty() && state.isNotFound) Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = PresentationText.Dynamic("Ничего не найдено").asString()
          )
      } else {
        Box {
          PriceFilterSection(
            modifier = Modifier.align(Alignment.TopEnd),
            isShowing = state.priceFilterIsShowing,
            onPriceAscendingFilter = {
              viewModel.onEvent(ProductListEvent.FilterByPriceClick(value = OrderType.Ascending))
            },
            onPriceDescendingFilter = {
              viewModel.onEvent(ProductListEvent.FilterByPriceClick(value = OrderType.Descending))
            }
          )

          AvailabilityFilterSection(
            isShowing = state.availabilityFilterIsShowing,
            modifier = Modifier
                .clickable {
                    viewModel.onEvent(ProductListEvent.FilterByAvailabilityClick(value = OrderType.Ascending))
                }
                .align(Alignment.TopEnd)
          )

          Column(Modifier.verticalScroll(scrollState)) {
            state.products.forEach { (product, quantity) ->

              DetailItem(
                onAddToCartClick = {
                  viewModel.onEvent(ProductListEvent.AddToBasket(value = product))
                },
                  onIncreaseProductCountClick = {
                      viewModel.onEvent(ProductListEvent.IncreaseQuantityToAdd(value = product))
                  },
                  onDecreaseProductCountClick = {
                      viewModel.onEvent(ProductListEvent.DecreaseQuantityToAdd(value = product))
                  },
                  onOpenProductCardClick = {
                      viewModel.onEvent(ProductListEvent.OpenProductDetails(value = product))
                  },
                  product = product,
                  isShowing = state.showDeliveryDialogs[product]!!,
                  onDeliveryProbabilityButtonClick = {
                      viewModel.onEvent(
                          ProductListEvent.DeliveryProbabilityDialogVisibilityChange(
                              value = true,
                              product
                          )
                      )
                  },
                  onDeliveryDialogDismiss = {
                      viewModel.onEvent(
                          ProductListEvent.DeliveryProbabilityDialogVisibilityChange(
                              value = false,
                              product
                          )
                      )
                  },
                  showBasket = settingsState.isLoggedIn,
                  quantity = quantity,
                  isShowingBasket = state.showBasketDialogs[product]!!,
                  onShowBasketClick = {
                      viewModel.onEvent(ProductListEvent.ShowingBasketDialog(value = true, product))
                  },
                  onDismissBasketDialog = {
                      viewModel.onEvent(
                          ProductListEvent.ShowingBasketDialog(
                              value = false,
                              product
                          )
                      )
                  },
                  goToBasket = {
                      viewModel.goToBasket()
                  }
              )
            }
          }
        }
      }
    }
  }
}

@Preview(
  name = "Product List Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProductListScreenPreview() {
  val navController = rememberNavController()
  val scaffoldState = rememberScaffoldState()
  val popupState = rememberPopupState()

  ProductListScreen(
    navController = navController,
    scaffoldState = scaffoldState,
    popupState = popupState,
    brand = "brand",
    number = "number",
  )
}
