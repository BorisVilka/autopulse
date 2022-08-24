package ru.autopulse05.android.feature.basket.presentation

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
import ru.autopulse05.android.feature.basket.domain.model.BasketItem
import ru.autopulse05.android.feature.basket.domain.repository.BasketRepository
import ru.autopulse05.android.feature.basket.domain.use_case.BasketUseCases
import ru.autopulse05.android.feature.basket.presentation.util.BasketEvent
import ru.autopulse05.android.feature.basket.presentation.util.BasketState
import ru.autopulse05.android.feature.basket.presentation.util.BasketUiEvent
import ru.autopulse05.android.feature.settings.presentation.Settings
import ru.autopulse05.android.feature.settings.presentation.SettingsViewModel
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
  application: Application,
  private val basketUseCases: BasketUseCases,
  ) : SettingsViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var clearBasketJob: Job? = null
  private var getBasketItemsJob: Job? = null
  private var updateBasketJob: Job? = null
  private val _uiEventChannel = Channel<BasketUiEvent>()

  var state by mutableStateOf(BasketState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getBasketItems() {
    getBasketItemsJob?.cancel()

  }

  private fun updateBasket() {
    updateBasketJob?.cancel()

    updateBasketJob = basketUseCases
      .update(
        login = settingsState.login,
        passwordHash = settingsState.passwordHash
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(isLoading = false)
          is Data.Error -> {
            Log.e(TAG, "Error during basket update: ${data.message}")

            _uiEventChannel.send(BasketUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
          is Data.Loading -> state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onCheckout() {
    if (state.selected.isNotEmpty()) viewModelScope.launch {
      _uiEventChannel.send(BasketUiEvent.Checkout(positions = state.selected.toList()))
    }
  }

  private fun onClear() {
    clearBasketJob = basketUseCases
      .clear(
        login = settingsState.login,
        passwordHash = settingsState.passwordHash,
        items = state.selected.toList()
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(isLoading = false)
          is Data.Error -> {
            Log.e(TAG, "Error during basket clear: ${data.message}")

            _uiEventChannel.send(BasketUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
          is Data.Loading -> state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  override fun onSettingsChange(settings: Settings) {
    super.onSettingsChange(settings)

    if (settings.isLoggedIn) {
      updateBasket()
      getBasketItems()
    } else viewModelScope.launch {
      _uiEventChannel.send(BasketUiEvent.NotLoggedIn)
    }
  }

  init {
    getSettings()
  }

  fun onEvent(event: BasketEvent) = when (event) {
    is BasketEvent.SelectAllChange -> state = state.copy(
      selected = if (event.value) {
        persistentListOf<BasketItem>().addAll(state.items)
      } else persistentListOf()
    )
    is BasketEvent.SelectChange -> state = state.copy(
      selected = state.selected.mutate {
        if (event.value) it.add(event.item)
        else it.remove(event.item)
      }
    )
    BasketEvent.Checkout -> onCheckout()
    BasketEvent.Clear -> onClear()
  }
}