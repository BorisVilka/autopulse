package ru.autopulse05.android.feature.topbar.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.settings.presentation.Settings
import ru.autopulse05.android.feature.settings.presentation.SettingsViewModel
import ru.autopulse05.android.feature.topbar.presentation.util.TopBarEvent
import ru.autopulse05.android.feature.topbar.presentation.util.TopBarState
import ru.autopulse05.android.feature.topbar.presentation.util.TopBarUiEvent
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor(
  application: Application
) : SettingsViewModel(application = application) {

  private val _uiEventChannel = Channel<TopBarUiEvent>()

  var state by mutableStateOf(TopBarState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onOpenDrawerClicked() {
    viewModelScope.launch {
      _uiEventChannel.send(TopBarUiEvent.OpenDrawerClicked)
    }
  }

  private fun onProfileClicked() {
    if (!state.isLoading) viewModelScope.launch {
        _uiEventChannel.send(
          if (settingsState.isLoggedIn) TopBarUiEvent.OpenUserMenu else TopBarUiEvent.SignIn
        )
    }
  }

  override fun onSettingsChange(settings: Settings) {
    super.onSettingsChange(settings)

    state = state.copy(isLoading = false)
  }

  init {
    getSettings()
  }

  fun onEvent(event: TopBarEvent) = when (event) {
    is TopBarEvent.OpenDrawerClicked -> onOpenDrawerClicked()
    is TopBarEvent.ProfileClicked -> onProfileClicked()
  }
}