package ru.autopulse05.android.feature.preferences.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.autopulse05.android.feature.preferences.presentation.ext.dataStore
import ru.autopulse05.android.shared.presentation.BaseViewModel

abstract class PreferencesViewModel(
  application: Application
) : BaseViewModel(application = application) {

  var preferencesState by mutableStateOf(Preferences())
  private var getPreferencesJob: Job? = null

  protected fun getPreferences() {
    getPreferencesJob?.cancel()

    getPreferencesJob = context.dataStore.data
      .onEach { preferences -> onPreferencesChange(preferences) }
      .launchIn(viewModelScope)
  }

  protected open fun onPreferencesChange(preferences: Preferences) {
    preferencesState = preferencesState.copy(
      login = preferences.login,
      passwordHash = preferences.passwordHash
    )
  }
}