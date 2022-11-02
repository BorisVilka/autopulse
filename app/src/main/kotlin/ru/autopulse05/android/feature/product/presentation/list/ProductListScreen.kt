package ru.autopulse05.android.feature.product.presentation.list

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.topbar.TopBar
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.cart.presentation.util.CartScreens
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.domain.util.OrderType
import ru.autopulse05.android.feature.product.presentation.list.components.AvailabilityFilterSection
import ru.autopulse05.android.feature.product.presentation.list.components.DetailItem
import ru.autopulse05.android.feature.product.presentation.list.components.PriceFilterSection
import ru.autopulse05.android.feature.product.presentation.list.util.ProductListEvent
import ru.autopulse05.android.feature.product.presentation.list.util.ProductListUiEvent
import ru.autopulse05.android.feature.product.presentation.util.ProductScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.CheckboxWithText
import ru.autopulse05.android.shared.presentation.components.SecondaryButton
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

  if(state.showBrands) {
      Dialog(onDismissRequest = {
                                viewModel.showBrands()
      }, ) {
          Card(modifier = Modifier.height(300.dp)
          ) {
              Column(modifier = Modifier
                  .background(MaterialTheme.colors.surface)
                  .verticalScroll(rememberScrollState())
                  .padding(SpaceNormal)
              ) {
                  state.brands.onEach {
                      CheckboxWithText(text = PresentationText.Dynamic(it), onClick = {
                          viewModel.changeBrand(it)
                      }, checked = state.chooised.contains(it))
                  }
              }
          }
      }
  }
  Scaffold(
    topBar = {
      TopBar(
        scaffoldState = scaffoldState,
        navController = navController,
        popupState = popupState,
        actions = {
            Spacer(modifier = Modifier.width(SpaceNormal))
          Row(modifier = Modifier.clickable {
              if(state.sortObject==null || state.sortObject==1) viewModel.setSort(0)
              else viewModel.setSort(1)
          }) {
              Text(text = "Цена")
              Icon(painter =  if(state.sortObject==null || state.sortObject!=0) painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24)
                else painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24),
                  contentDescription = "")
          }
            Spacer(modifier = Modifier.width(SpaceSmall))
           Row(modifier = Modifier.clickable {
               if(state.sortObject==null || state.sortObject==3) viewModel.setSort(2)
               else viewModel.setSort(3)
            }) {
                Text(text = "Срок")
                Icon(painter =
                    if(state.sortObject==null || state.sortObject!=2) painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24)
                    else painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24),
                    contentDescription = "")
            }
            Spacer(modifier = Modifier.width(SpaceSmall))
            Row(modifier = Modifier.clickable {
                viewModel.showBrands()
            }) {
                Text(text = "Бренды")
                Icon(painter =
                if(!state.showBrands) painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24)
                else painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24),
                    contentDescription = "")
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

          LazyColumn {
              var list = state.products
                  .toList()
                  .filter {
                      state.chooised.contains(it.first.brand) || state.chooised.isEmpty()
                  }
              if(state.sortObject!=null) {
                  if(state.sortObject==0) list = list.sortedBy { it.first.price }
                  else if(state.sortObject==1) list = list.sortedBy { -it.first.price }
                  else if(state.sortObject==2) list = list.sortedBy { it.first.deliveryPeriod }
                  else list = list.sortedBy { -it.first.deliveryPeriod }
              }
              items(
                  list
                  .orEmpty()) { product ->
                  DetailItem(
                      onAddToCartClick = {
                          viewModel.onEvent(ProductListEvent.AddToBasket(value = product.first))
                      },
                      onIncreaseProductCountClick = {
                          viewModel.onEvent(ProductListEvent.IncreaseQuantityToAdd(value = product.first))
                      },
                      onDecreaseProductCountClick = {
                          viewModel.onEvent(ProductListEvent.DecreaseQuantityToAdd(value = product.first))
                      },
                      onOpenProductCardClick = {
                          viewModel.onEvent(ProductListEvent.OpenApplication(value = product.first))
                      },
                      product = product.first,
                      isShowing = state.showDeliveryDialogs[product.first]!!,
                      showInfo = state.showInfoDialogs[product.first]!!,
                      onOpenInfoDialog = {
                          viewModel.onEvent(ProductListEvent.OpenInfoDialog(
                              value = true,
                              product = product.first
                            )
                          )
                      },
                      onDismissInfoDialog = {
                          viewModel.onEvent(ProductListEvent.OpenInfoDialog(
                              value = false,
                              product = product.first
                             )
                          )
                      },
                      onDeliveryProbabilityButtonClick = {
                          viewModel.onEvent(
                              ProductListEvent.DeliveryProbabilityDialogVisibilityChange(
                                  value = true,
                                  product.first
                              )
                          )
                      },
                      onDeliveryDialogDismiss = {
                          viewModel.onEvent(
                              ProductListEvent.DeliveryProbabilityDialogVisibilityChange(
                                  value = false,
                                  product.first
                              )
                          )
                      },
                      showBasket = settingsState.isLoggedIn,
                      quantity = product.second,
                      isShowingBasket = state.showBasketDialogs[product.first]!!,
                      onShowBasketClick = {
                          viewModel.onEvent(ProductListEvent.ShowingBasketDialog(value = true, product.first))
                      },
                      onDismissBasketDialog = {
                          viewModel.onEvent(
                              ProductListEvent.ShowingBasketDialog(
                                  value = false,
                                  product.first
                              )
                          )
                      },
                      goToBasket = {
                          viewModel.goToBasket()
                      },
                      onOpenProductDetails = {
                          if(product.first==null) {
                              Toast.makeText(context,"Товар не найден",Toast.LENGTH_LONG).show()
                          }else viewModel.onEvent(ProductListEvent.OpenProductDetails(value = product.first)) }
                  )
              }
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
                              modifier = Modifier
                                  .weight(0.33f)
                                  .padding(SpaceSmall),
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
                              modifier = Modifier
                                  .weight(0.33f)
                                  .padding(SpaceSmall),
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
                              Spacer(modifier = Modifier.width(SpaceNormal))
                              Text(
                                  text = "Период:\n${it.period}",
                                  style = MaterialTheme.typography.subtitle1
                              )
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
