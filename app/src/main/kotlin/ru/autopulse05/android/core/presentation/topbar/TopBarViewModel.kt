package ru.autopulse05.android.core.presentation.topbar

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.core.presentation.topbar.util.TopBarEvent
import ru.autopulse05.android.core.presentation.topbar.util.TopBarState
import ru.autopulse05.android.core.presentation.topbar.util.TopBarUiEvent
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor(
  application: Application
) : PreferencesViewModel(application = application) {

  private val _uiEventChannel = Channel<TopBarUiEvent>()

  var state by mutableStateOf(TopBarState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onOpenDrawer() {
    viewModelScope.launch {
      _uiEventChannel.send(TopBarUiEvent.OpenDrawer)
    }
  }

  private fun onProfileClick() {
    if (!state.isLoading) viewModelScope.launch {
        _uiEventChannel.send(
          if (preferencesState.isLoggedIn) TopBarUiEvent.OpenUserMenu else TopBarUiEvent.SignIn
        )
    }
  }

  private fun onSearch() {
    viewModelScope.launch {
      _uiEventChannel.send(TopBarUiEvent.Search)
    }
  }

  override fun onPreferencesChange(preferences: Preferences) {
    super.onPreferencesChange(preferences)

    state = state.copy(isLoading = false)
  }

  init {
    getPreferences()
  }

  fun onEvent(event: TopBarEvent) = when (event) {
    is TopBarEvent.OpenDrawer -> onOpenDrawer()
    is TopBarEvent.ProfileClick -> onProfileClick()
    is TopBarEvent.Search -> onSearch()
  }
}