package ru.autopulse05.android.feature.product.presentation.crosse

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
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.cart.domain.use_case.CartUseCases
import ru.autopulse05.android.feature.product.domain.model.Crosse
import ru.autopulse05.android.feature.product.domain.model.toPosition
import ru.autopulse05.android.feature.product.domain.use_case.ProductUseCases
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.presentation.crosse.util.ProductCrosseEvent
import ru.autopulse05.android.feature.product.presentation.crosse.util.ProductCrosseState
import ru.autopulse05.android.feature.product.presentation.crosse.util.ProductCrosseUiEvent
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class CrosseViewModel @Inject constructor(
  application: Application,
  private val productUseCases: ProductUseCases,
  private val cartUseCases: CartUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getInfoJob: Job? = null
  private val _uiEventChannel = Channel<ProductCrosseUiEvent>()

  var state by mutableStateOf(ProductCrosseState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getInfo() {
    val login: String
    val passwordHash: String

    login = preferencesState.adminLogin
    passwordHash = preferencesState.adminPasswordHash

    val crosse = state.crosse!!

    getInfoJob?.cancel()

    getInfoJob = productUseCases
      .getArticlesInfo(
        login = login,
        passwordHash = passwordHash,
        brand = crosse.brand,
        number = crosse.number,
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

            _uiEventChannel.send(ProductCrosseUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
        }
      }
      .launchIn(viewModelScope)

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

              _uiEventChannel.send(ProductCrosseUiEvent.Toast(text = stringResource(R.string.error)))

              state = state.copy(isLoading = false)
            }
          }
        }
        .launchIn(viewModelScope)
    }
  }

  private fun onIncreaseQuantityToAdd() {
    state = state.copy(
      quantity = state.quantity + if (state.packing != 0) state.packing else 1
    )
  }

  private fun onDecreaseQuantityToAdd() {
    if (state.quantity > state.packing) {
      state = state.copy(
        quantity = state.quantity - if (state.packing != 0) state.packing else 1
      )
    }
  }

  private fun onInitialValuesChange(crosse: Crosse, product: Product) {
    state = state.copy(
      crosse = crosse,
      product = product
    )

    getInfo()
  }

  init {
    getPreferences()
  }

  fun onEvent(event: ProductCrosseEvent): Unit = when (event) {
    is ProductCrosseEvent.InitialValuesChange -> onInitialValuesChange(event.crosse, event.product)
    is ProductCrosseEvent.AddToBasket -> onAddToBasket()
    is ProductCrosseEvent.DecreaseQuantityToAdd -> onDecreaseQuantityToAdd()
    is ProductCrosseEvent.IncreaseQuantityToAdd -> onIncreaseQuantityToAdd()
  }
}