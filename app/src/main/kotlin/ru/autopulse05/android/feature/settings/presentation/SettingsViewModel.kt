package ru.autopulse05.android.feature.settings.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.autopulse05.android.feature.settings.presentation.ext.dataStore
import ru.autopulse05.android.shared.presentation.BaseViewModel

abstract class SettingsViewModel(
  application: Application
) : BaseViewModel(application = application) {

  var settingsState by mutableStateOf(Settings())
  private var getSettingsJob: Job? = null

  protected fun getSettings() {
    getSettingsJob?.cancel()

    getSettingsJob = context.dataStore.data
      .onEach { settings -> onSettingsChange(settings) }
      .launchIn(viewModelScope)
  }

  protected open fun onSettingsChange(settings: Settings) {
    settingsState = settingsState.copy(
      login = settings.login,
      passwordHash = settings.passwordHash
    )
  }
}