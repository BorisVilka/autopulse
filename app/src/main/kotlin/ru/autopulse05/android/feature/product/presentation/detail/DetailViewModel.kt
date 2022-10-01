package ru.autopulse05.android.feature.product.presentation.detail

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.domain.model.toPosition
import ru.autopulse05.android.feature.product.domain.use_case.ProductUseCases
import ru.autopulse05.android.feature.product.presentation.detail.util.ProductDetailsEvent
import ru.autopulse05.android.feature.product.presentation.detail.util.ProductDetailsState
import ru.autopulse05.android.feature.product.presentation.detail.util.ProductDetailsUiEvent
import ru.autopulse05.android.feature.product.presentation.list.util.ProductListEvent
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  application: Application,
  private val productUseCases: ProductUseCases,
  private val cartUseCases: CartUseCases,
  private val laximoUseCases: LaximoUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }
  private var getCatalogsJob: Job? = null
  private var getApplicationJob: Job? = null
  private var getInfoJob: Job? = null
  private val _uiEventChannel = Channel<ProductDetailsUiEvent>()

  var state by mutableStateOf(ProductDetailsState())

  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getInfo() {
    val login: String
    val passwordHash: String

    login = preferencesState.adminLogin
    passwordHash = preferencesState.adminPasswordHash

    val product = state.product!!

    getInfoJob?.cancel()

    getInfoJob = productUseCases
      .getArticlesInfo(
        login = login,
        passwordHash = passwordHash,
        brand = product.brand,
        number = product.number,
        locale = preferencesState.locale
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(
              info = data.value,
              isLoading = false
          )
          is Data.Loading -> state.copy(isLoading = true)
          is Data.Error -> {
            Log.e(TAG, "Error during getting info: ${data.message}")

            _uiEventChannel.send(ProductDetailsUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
        }
      }
      .launchIn(viewModelScope)

  }

  fun getApplicationLaximo() {
    getCatalogsJob?.cancel()
    getCatalogsJob = laximoUseCases.getCatalogs(
      login = preferencesState.laximoLogin,
      password = preferencesState.laximoPassword,
      locale =  "ru_RU"
    ).onEach { data ->
      when(data) {
        is Data.Success -> {
          for(i in data.value.indices) {
            Log.d("TAG",data.value[i].brand+" ${data.value[i].name} ${data.value[i].code} "+state.product!!.brand)
            if(state.product!!.brand.lowercase().contains(data.value[i].brand.lowercase())) {
              getApplicationJob?.cancel()
              getApplicationJob = laximoUseCases.getApplication(
                login = preferencesState.laximoLogin,
                password = preferencesState.laximoPassword,
                catalog = state.info!!.number.trim(),
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

  private fun onAddToBasket() {
    if (state.quantity > 0) {
      val info = state.info!!

      cartUseCases
        .add(
          login = preferencesState.login,
          passwordHash = preferencesState.passwordHash,
          positions = state.product!!.toPosition(state.quantity)
        )
        .onEach { data ->
          when (data) {
            is Data.Success -> state = state.copy()
            is Data.Loading -> {}
            is Data.Error -> {
              Log.e(TAG, "Error during adding to basket: ${data.message}")

              _uiEventChannel.send(ProductDetailsUiEvent.Toast(text = stringResource(R.string.error)))

              state = state.copy(isLoading = false)
            }
          }
        }
        .launchIn(viewModelScope)
    }
  }


  private fun onOpenCrosseDetails(value: Crosse) {
    viewModelScope.launch {
      _uiEventChannel.send(
        ProductDetailsUiEvent.GoToCrosse(
          product = state.product!!,
          crosse = value
        )
      )
    }
  }

  private fun onIncreaseQuantityToAdd() {
    if (!(state.quantity >= state.product?.packing!! && state.quantity < state.product?.availability!!)) return
    state = state.copy(
      quantity = state.quantity + if (state.packing != 0) state.packing else 1
    )
  }

  private fun onDecreaseQuantityToAdd() {
    if (!(state.quantity > state.product?.packing!! && state.quantity <= state.product?.availability!!)) return
    if (state.quantity > state.packing) {
      state = state.copy(
        quantity = state.quantity - if (state.packing != 0) state.packing else 1
      )
    }
  }

  private fun onInitialValuesChange(product: Product) {
    state = state.copy(
      product = product
    )
    state = state.copy(
      quantity = product.packing
    )
    getInfo()
  }

  init {
    getPreferences()
  }

  fun onEvent(event: ProductDetailsEvent): Unit = when (event) {
    is ProductDetailsEvent.InitialValuesChange -> onInitialValuesChange(event.product)
    is ProductDetailsEvent.OpenCrosseDetails -> onOpenCrosseDetails(event.value)
    is ProductDetailsEvent.AddToBasket -> onAddToBasket()
    is ProductDetailsEvent.DecreaseQuantityToAdd -> onDecreaseQuantityToAdd()
    is ProductDetailsEvent.IncreaseQuantityToAdd -> onIncreaseQuantityToAdd()
    is ProductDetailsEvent.DeliveryProbabilityDialogVisibilityChange -> state = state.copy(
      showDeliveryDialog = event.value
    )
  }
}