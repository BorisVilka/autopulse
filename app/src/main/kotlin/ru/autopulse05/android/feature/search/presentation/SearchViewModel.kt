package ru.autopulse05.android.feature.search.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.search.domain.model.HistoryItem
import ru.autopulse05.android.feature.search.domain.model.SearchTip
import ru.autopulse05.android.feature.search.domain.use_case.SearchUseCases
import ru.autopulse05.android.feature.search.presentation.util.SearchEvent
import ru.autopulse05.android.feature.search.presentation.util.SearchState
import ru.autopulse05.android.feature.search.presentation.util.SearchUiEvent
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  application: Application,
  private val searchUseCases: SearchUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getHistoryJob: Job? = null
  private var getTipsJob: Job? = null
  private val _uiEventChannel = Channel<SearchUiEvent>()

  var state by mutableStateOf(SearchState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onInitialValuesChange(number: String) {
    if (number.isNotBlank()) {
      state = state.copy(number = number)
      getTips()
    }
  }

  private fun onNumberChange(value: String) {
    state = state.copy(number = value)

    if (value.isNotBlank()) {
      val numberResult = searchUseCases.validateNumber(value = value)
      val hasError = listOf(numberResult).any { it is Data.Error }

      if (!hasError) viewModelScope.launch {
        delay(500L)

        state = state.copy(
          didRequest = false
        )

        getTips()
      }
    } else state = state.copy(showHistory = true)
  }

  private fun getHistory() {
    getHistoryJob?.cancel()

    getHistoryJob = searchUseCases
      .getHistory(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> state = state.copy(historyItems = data.value)
          is Data.Error -> {
            Log.e(TAG, "Error during getting history: ${data.message}")

            _uiEventChannel.send(SearchUiEvent.Toast(text = stringResource(R.string.error)))
          }
          is Data.Loading -> {}
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getTips() {
    val login: String
    val passwordHash: String

    if (preferencesState.isLoggedIn) {
      login = preferencesState.login
      passwordHash = preferencesState.passwordHash
    } else {
      login = preferencesState.adminLogin
      passwordHash = preferencesState.adminPasswordHash
    }

    getTipsJob?.cancel()

    searchUseCases
      .getTips(
        login = login,
        passwordHash = passwordHash,
        number = state.number,
        locale = preferencesState.locale
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> state = state.copy(
            tips = data.value,
            showHistory = false,
            didRequest = true
          )
          is Data.Error -> {
            Log.e(TAG, "Error during getting tips: ${data.message}")

            _uiEventChannel.send(SearchUiEvent.Toast(text = stringResource(R.string.error)))

          }
          is Data.Loading -> {}
        }
      }
      .launchIn(viewModelScope)
  }

  override fun onPreferencesChange(preferences: Preferences) {
    super.onPreferencesChange(preferences)

    if (preferences.isLoggedIn) {
      getHistory()
    }
  }

  init {
    getPreferences()
  }

  private fun onTipClick(value: SearchTip) {
    viewModelScope.launch {
      _uiEventChannel.send(SearchUiEvent.GoToList(brand = value.brand, number = value.number))
    }
  }

  private fun onSearchItemClick(value: HistoryItem) {
    viewModelScope.launch {
      _uiEventChannel.send(SearchUiEvent.GoToList(brand = value.brand, number = value.number))
    }
  }

  fun onEvent(event: SearchEvent): Unit = when (event) {
    is SearchEvent.NumberChange -> onNumberChange(value = event.value)
    is SearchEvent.TipClick -> onTipClick(event.value)
    is SearchEvent.InitialValuesChange -> onInitialValuesChange(number = event.number)
    is SearchEvent.HistoryItemClick -> onSearchItemClick(event.value)
  }
}