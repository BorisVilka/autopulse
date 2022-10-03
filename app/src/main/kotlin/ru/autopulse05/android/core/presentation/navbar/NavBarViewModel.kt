package ru.autopulse05.android.core.presentation.navbar

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
import ru.autopulse05.android.core.presentation.navbar.util.NavBarEvent
import ru.autopulse05.android.core.presentation.navbar.util.NavBarItems
import ru.autopulse05.android.core.presentation.navbar.util.NavBarState
import ru.autopulse05.android.core.presentation.navbar.util.NavBarUiEvent
import ru.autopulse05.android.feature.cart.domain.repository.CartRepository
import ru.autopulse05.android.feature.cart.domain.use_case.CartUseCases
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject


@HiltViewModel
class NavBarViewModel @Inject constructor(
  application: Application,
  private val cartUseCases: CartUseCases,
  private val cartRepository: CartRepository
) : PreferencesViewModel(application = application) {


  private var updateBasketJob: Job? = null
  private var getBasketItemsCountJob: Job? = null
  private val _uiEventChannel = Channel<NavBarUiEvent>()

  var state by mutableStateOf(NavBarState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getBasketItemsCount() {
    getBasketItemsCountJob?.cancel()

    getBasketItemsCountJob = cartRepository
      .count()
      .onEach {
        state = state.copy(basketItemsCount = it)
      }
      .launchIn(viewModelScope)
  }

  private fun onNavBarItemClick(value: NavBarItems) {
    viewModelScope.launch {
      _uiEventChannel.send(NavBarUiEvent.OnNavBarItemClick(value = value))
    }
  }

  private fun updateBasket() {
    updateBasketJob?.cancel()

    updateBasketJob = cartUseCases
      .update(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash
      )
      .launchIn(viewModelScope)
  }

  override fun onPreferencesChange(preferences: Preferences) {
    super.onPreferencesChange(preferences)

    if (preferences.isLoggedIn) {
      updateBasket()
      getBasketItemsCount()
    }
  }

  init {
    getPreferences()
  }

  fun onEvent(event: NavBarEvent) = when (event) {
    is NavBarEvent.OnNavBarItemClick -> onNavBarItemClick(event.value)
  }
}