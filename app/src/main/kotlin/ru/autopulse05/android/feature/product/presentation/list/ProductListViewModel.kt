package ru.autopulse05.android.feature.product.presentation.list

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.cart.domain.use_case.CartUseCases
import ru.autopulse05.android.feature.laximo.domain.use_case.LaximoUseCases
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.domain.model.toPosition
import ru.autopulse05.android.feature.product.domain.util.OrderType
import ru.autopulse05.android.feature.product.presentation.list.util.ProductListEvent
import ru.autopulse05.android.feature.product.presentation.list.util.ProductListState
import ru.autopulse05.android.feature.product.presentation.list.util.ProductListUiEvent
import ru.autopulse05.android.feature.search.domain.use_case.SearchUseCases
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class ProductListViewModel @Inject constructor(
  application: Application,
  private val cartUseCases: CartUseCases,
  private val searchUseCases: SearchUseCases,
  private val laximoUseCases: LaximoUseCases,
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }
  private var getCatalogsJob: Job? = null
  private var getApplicationJob: Job? = null
  private var getProductsJob: Job? = null
  private val _uiEventChannel = Channel<ProductListUiEvent>()

  var state by mutableStateOf(ProductListState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  fun getApplicationLaximo(value: Product) {
    getCatalogsJob?.cancel()
    getCatalogsJob = laximoUseCases.getCatalogs(
      login = preferencesState.laximoLogin,
      password = preferencesState.laximoPassword,
      locale =  "ru_RU"
    ).onEach { data ->
      when(data) {
        is Data.Success -> {
          for(i in data.value.indices) {
            if(value.brand.lowercase().contains(data.value[i].brand.lowercase())) {
              getApplicationJob?.cancel()
              getApplicationJob = laximoUseCases.getApplication(
                login = preferencesState.laximoLogin,
                password = preferencesState.laximoPassword,
                catalog = value.number.trim(),
                ssd = "",
                oem = data.value[i].code.trim(),
                locale = preferencesState.locale
              ).onEach { data ->
                when(data) {
                  is Data.Success -> {
                    Log.d("TAG","SUCCESS ${data.value.size}")
                    state = state.copy(
                      showApplication = true,
                      applications = data.value
                    )
                  }
                  is Data.Error -> {
                    Log.d("TAG","ERROR ${data.message}")
                  }
                }
              }
                .launchIn(viewModelScope)
            }
          }
        }
      }
    }.launchIn(viewModelScope)
  }

  private fun getProducts() {
    val login: String
    val passwordHash: String

    if (preferencesState.isLoggedIn) {
      login = preferencesState.login
      passwordHash = preferencesState.passwordHash
    } else {
      login = preferencesState.adminLogin
      passwordHash = preferencesState.adminPasswordHash
    }

    getProductsJob?.cancel()

    getProductsJob = searchUseCases
      .getArticle(
        login = login,
        passwordHash = passwordHash,
        number = state.number,
        brand = state.brand
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> {
            var list = mutableListOf<String>()
            for(i in data.value) {
              if(!list.contains(i.brand)) {
                list.add(i.brand)
              }
            }
            state.copy(
              isLoading = false,
              isNotFound = false,
              products = data.value
                .sortedWith(
                  compareBy({ it.brand != state.brand },
                    { it.deliveryPeriod },
                    { it.availability })
                )
                .associateWith { min(1, it.availability) }
                .toPersistentMap(),
              showBasketDialogs = data.value
                .sortedWith(
                  compareBy({ it.brand != state.brand },
                    { it.deliveryPeriod },
                    { it.availability })
                )
                .associateWith { false }
                .toPersistentMap(),
              showDeliveryDialogs = data.value
                .sortedWith(
                  compareBy({ it.brand != state.brand },
                    { it.deliveryPeriod },
                    { it.availability })
                )
                .associateWith { false }
                .toPersistentMap(),
              showInfoDialogs = data.value
                .sortedWith(
                  compareBy({ it.brand != state.brand },
                    { it.deliveryPeriod },
                    { it.availability })
                )
                .associateWith { false }
                .toPersistentMap(),
              brands = list.toList()
            )
          }
          is Data.Error -> {
            if (data.code == 404) {
              state = state.copy(isNotFound = true)
            } else {
              Log.e(TAG, "Error during getting products: ${data.message}")

              _uiEventChannel.send(ProductListUiEvent.Toast(text = stringResource(R.string.error)))
            }

            state.copy(isLoading = false, isNotFound = true)
          }
          is Data.Loading -> state.copy(isLoading = true, isNotFound = false)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onInitialValuesChange(brand: String, number: String) {
    state = state.copy(
      brand = brand,
      number = number
    )

    getProducts()
  }

  private fun onOpenProductDetails(value: Product) {
    viewModelScope.launch {
      _uiEventChannel.send(ProductListUiEvent.GoToProductDetails(value = value))
    }
  }

  private fun onFilterByPrice(orderType: OrderType) {
    val sortedList = when (orderType) {
      is OrderType.Ascending -> state.products.toList().sortedBy { it.first.price }
      is OrderType.Descending -> state.products.toList().sortedByDescending { it.first.price }
    }

    state = state.copy(
      products = sortedList.toMap().toPersistentMap(),
      priceFilterIsShowing = false
    )
  }

  private fun onFilterByAvailability(orderType: OrderType) {
    val sortedList = when (orderType) {
      is OrderType.Ascending -> state.products.toList().sortedBy { it.first.availability }
      is OrderType.Descending -> state.products.toList().sortedByDescending { it.first.availability }
    }

    state = state.copy(
      products = sortedList.toMap().toPersistentMap(),
      availabilityFilterIsShowing = false
    )
  }

  private fun onAddedToBasket(value: Product) {
    val quantity = state.products[value]!!

    if (quantity > 0) {
      viewModelScope.launch {
        cartUseCases.add(
          login = preferencesState.login,
          passwordHash = preferencesState.passwordHash,
          positions = value.toPosition(quantity)
        ).collect { data ->
          when (data) {
            is Data.Success -> state = state.copy(

            )
            is Data.Loading -> {}
            is Data.Error -> {
              Log.e(TAG, "Error during adding to basket: ${data.message}")

              _uiEventChannel.send(ProductListUiEvent.Toast(text = stringResource(R.string.error)))
            }
          }
        }
      }
    }
  }

  private fun onIncreaseQuantityToAdd(value: Product) {
    if (state.products[value] == value.availability) return
    state = state.copy(
      products = state.products.mutate {
        val quantity = it[value]!!
        it[value] = quantity + if (value.packing != 0) value.packing else 1
      }
    )
  }

  private fun onDecreaseQuantityToAdd(value: Product) {
    if (state.products[value] == value.packing) return
    state = state.copy(
      products = state.products.mutate {
        val quantity = it[value]!!
        it[value] = quantity - if (value.packing != 0) value.packing else 1
      }
    )
  }

  init {
    getPreferences()
  }

  fun goToBasket() {
    viewModelScope.launch {
      _uiEventChannel.send(ProductListUiEvent.GoToBasket)
    }
  }

  fun onEvent(event: ProductListEvent): Unit = when (event) {
    is ProductListEvent.InitialValuesChange -> onInitialValuesChange(
      brand = event.brand,
      number = event.number
    )
    is ProductListEvent.OpenProductDetails ->onOpenProductDetails(value = event.value)
    is ProductListEvent.OpenApplication -> getApplicationLaximo(event.value)
    is ProductListEvent.FilterByPriceClick -> onFilterByPrice(event.value)
    is ProductListEvent.PriceFilterVisibilityChange -> state = state.copy(
      priceFilterIsShowing = event.value,
      availabilityFilterIsShowing = false
    )
    is ProductListEvent.FilterByAvailabilityClick -> onFilterByAvailability(event.value)
    is ProductListEvent.AvailabilityFilterVisibilityChange -> state = state.copy(
      priceFilterIsShowing = false,
      availabilityFilterIsShowing = event.value
    )
    is ProductListEvent.AddToBasket -> onAddedToBasket(event.value)
    is ProductListEvent.IncreaseQuantityToAdd -> onIncreaseQuantityToAdd(event.value)
    is ProductListEvent.DecreaseQuantityToAdd -> onDecreaseQuantityToAdd(event.value)
    is ProductListEvent.DeliveryProbabilityDialogVisibilityChange -> state = state.copy(
      showDeliveryDialogs = state.showDeliveryDialogs.put(event.product, event.value)
    )
    is ProductListEvent.ShowingBasketDialog -> state = state.copy(
      showBasketDialogs = state.showBasketDialogs.put(event.product, event.value)
    )
    is ProductListEvent.OpenInfoDialog -> state = state.copy(
      showInfoDialogs = state.showInfoDialogs.put(event.product,event.value)
    )
  }

  fun setSort(it: Int) {
    state = state.copy(
      sortObject = it
    )
  }

  fun showBrands() {
    state = state.copy(
      showBrands = !state.showBrands
    )
  }
  fun changeBrand(it: String) {
    if(state.chooised.contains(it)) {
      val mutable = mutableListOf<String>()
      mutable.addAll(state.chooised)
      mutable.remove(it)
      state = state.copy(
        chooised = mutable.toList()
      )
    } else {
      val mutable = mutableListOf<String>()
      mutable.addAll(state.chooised)
      mutable.add(it)
      state = state.copy(
        chooised = mutable.toList()
      )
    }
  }
}