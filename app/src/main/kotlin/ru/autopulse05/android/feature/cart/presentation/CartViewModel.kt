package ru.autopulse05.android.feature.cart.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.cart.domain.repository.CartRepository
import ru.autopulse05.android.feature.cart.domain.use_case.CartUseCases
import ru.autopulse05.android.feature.cart.presentation.util.CartEvent
import ru.autopulse05.android.feature.cart.presentation.util.CartState
import ru.autopulse05.android.feature.cart.presentation.util.CartUiEvent
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
  application: Application,
  private val cartUseCases: CartUseCases,
  private val cartRepository: CartRepository
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var clearBasketJob: Job? = null
  private var getBasketItemsJob: Job? = null
  private var updateBasketJob: Job? = null
  private val _uiEventChannel = Channel<CartUiEvent>()

  var state by mutableStateOf(CartState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getBasketItems() {
    getBasketItemsJob?.cancel()

    getBasketItemsJob = cartRepository
      .getAll()
      .onEach { entities ->
        state = state.copy(
          items = entities,
          selected = persistentListOf<CartItem>().addAll(entities)
        )
      }
      .launchIn(viewModelScope)
  }

  private fun updateBasket() {
    updateBasketJob?.cancel()

    updateBasketJob = cartUseCases
      .update(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> {
            state.copy(isLoading = false,
              items = data.value,
              selected = persistentListOf<CartItem>().addAll(data.value)
            )
          }
          is Data.Error -> {
            Log.e(TAG, "Error during basket update: ${data.message}")

            _uiEventChannel.send(CartUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
          is Data.Loading -> state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onCheckout() {
    if (state.selected.isNotEmpty()) viewModelScope.launch {
      _uiEventChannel.send(CartUiEvent.Checkout(positions = state.selected.toList()))
    }
  }

  private fun onClear() {
    clearBasketJob?.cancel()
    if(state.selected.size == state.items.size) {
      clearBasketJob = cartUseCases
        .clearAllUseCase(
          login = preferencesState.login,
          passwordHash = preferencesState.passwordHash
        )
        .onEach { data ->
          state = when (data) {
            is Data.Success -> state.copy(isLoading = false, items = listOf(), selected = persistentListOf())
            is Data.Error -> {
              Log.e(TAG, "Error during basket clear: ${data.message}")

              _uiEventChannel.send(CartUiEvent.Toast(text = stringResource(R.string.error)))

              state.copy(isLoading = false)
            }
            is Data.Loading -> state.copy(isLoading = true)
          }
        }
        .launchIn(viewModelScope)
    } else {
      clearBasketJob = cartUseCases
        .clear(
          login = preferencesState.login,
          passwordHash = preferencesState.passwordHash,
          items = state.selected.toList()
        )
        .onEach { data ->
          state = when (data) {
            is Data.Success -> state.copy(isLoading = false, items = data.value, selected = persistentListOf<CartItem>().addAll(data.value))
            is Data.Error -> {
              Log.e(TAG, "Error during basket clear: ${data.message}")

              _uiEventChannel.send(CartUiEvent.Toast(text = stringResource(R.string.error)))

              state.copy(isLoading = false)
            }
            is Data.Loading -> state.copy(isLoading = true)
          }
        }
        .launchIn(viewModelScope)
    }
  }

  override fun onPreferencesChange(preferences: Preferences) {
    super.onPreferencesChange(preferences)

    if (preferences.isLoggedIn) {
      updateBasket()
    } else viewModelScope.launch {
      _uiEventChannel.send(CartUiEvent.NotLoggedIn)
    }
  }

  init {
    getPreferences()
  }

  fun onEvent(event: CartEvent) = when (event) {
    is CartEvent.SelectAllChange -> state = state.copy(
      selected = if (event.value) {
        persistentListOf<CartItem>().addAll(state.items)
      } else persistentListOf()
    )
    is CartEvent.SelectChange -> state = state.copy(
      selected = state.selected.mutate {
        if (event.value) it.add(event.item)
        else it.remove(event.item)
      }
    )
    CartEvent.Checkout -> onCheckout()
    CartEvent.Clear -> onClear()
  }
}