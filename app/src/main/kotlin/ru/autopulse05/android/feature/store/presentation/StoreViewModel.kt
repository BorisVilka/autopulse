package ru.autopulse05.android.feature.store.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.search.presentation.util.SearchState
import ru.autopulse05.android.feature.store.presentation.util.MenuItems
import ru.autopulse05.android.feature.store.presentation.util.StoreEvent
import ru.autopulse05.android.feature.store.presentation.util.StoreUiEvent
import ru.autopulse05.android.shared.domain.use_case.SharedUseCases
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
  application: Application,
  private val sharedUseCases: SharedUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private val _uiEventChannel = Channel<StoreUiEvent>()

  var state by mutableStateOf(SearchState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onMenuItemClick(value: MenuItems) {
    viewModelScope.launch {
      _uiEventChannel.send(StoreUiEvent.MenuItemClick(value = value))
    }
  }

  private fun onCallSupport() {
    sharedUseCases.dialTo(preferencesState.supportPhone).let { data ->
      when (data) {
        is Data.Success -> {}
        else -> {
          data as Data.Error
          Log.e(TAG, "Error while dialing to support: ${data.message}")
        }
      }
    }
  }

  private fun onWriteToSupport() {
    sharedUseCases.mailTo(preferencesState.supportEmail).let { data ->
      when (data) {
        is Data.Success -> {}
        else -> {
          data as Data.Error
          Log.e(TAG, "Error while writing to support: ${data.message}")
        }
      }
    }
  }

  init {
    getPreferences()
  }

  fun onEvent(event: StoreEvent): Unit = when (event) {
    is StoreEvent.MenuItemClick -> onMenuItemClick(event.value)
    is StoreEvent.CallSupport -> onCallSupport()
    is StoreEvent.WriteToSupport -> onWriteToSupport()
  }
}