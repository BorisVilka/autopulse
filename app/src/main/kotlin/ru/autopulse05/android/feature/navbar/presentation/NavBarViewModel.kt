package ru.autopulse05.android.feature.navbar.presentation

import android.app.Application
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
import ru.autopulse05.android.feature.basket.domain.repository.BasketRepository
import ru.autopulse05.android.feature.basket.domain.use_case.BasketUseCases
import ru.autopulse05.android.feature.navbar.presentation.util.NavBarEvent
import ru.autopulse05.android.feature.navbar.presentation.util.NavBarItems
import ru.autopulse05.android.feature.navbar.presentation.util.NavBarState
import ru.autopulse05.android.feature.navbar.presentation.util.NavBarUiEvent
import ru.autopulse05.android.feature.settings.presentation.Settings
import ru.autopulse05.android.feature.settings.presentation.SettingsViewModel
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject


@HiltViewModel
class NavBarViewModel @Inject constructor(
  application: Application,
  private val basketUseCases: BasketUseCases,
 ) : SettingsViewModel(application = application) {


  private var updateBasketJob: Job? = null
  private var getBasketItemsCountJob: Job? = null
  private val _uiEventChannel = Channel<NavBarUiEvent>()

  var state by mutableStateOf(NavBarState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getBasketItemsCount() {
    getBasketItemsCountJob?.cancel()

  }

  private fun onNavBarItemClick(value: NavBarItems) {
    viewModelScope.launch {
      _uiEventChannel.send(NavBarUiEvent.OnNavBarItemClick(value = value))
    }
  }

  private fun updateBasket() {
    updateBasketJob?.cancel()

    updateBasketJob = basketUseCases
      .update(
        login = settingsState.login,
        passwordHash = settingsState.passwordHash
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> {}
          is Data.Error -> {}
          is Data.Loading -> {}
        }
      }
      .launchIn(viewModelScope)
  }

  override fun onSettingsChange(settings: Settings) {
    super.onSettingsChange(settings)

    if (settings.isLoggedIn) {
      updateBasket()
      getBasketItemsCount()
    }
  }

  init {
    getSettings()
  }

  fun onEvent(event: NavBarEvent) = when (event) {
    is NavBarEvent.OnNavBarItemClick -> onNavBarItemClick(event.value)
  }
}