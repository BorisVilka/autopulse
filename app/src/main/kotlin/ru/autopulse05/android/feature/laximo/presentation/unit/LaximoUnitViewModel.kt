package ru.autopulse05.android.feature.laximo.presentation.unit

import android.app.Application
import android.util.Log
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
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.laximo.domain.model.LaximoDetail
import ru.autopulse05.android.feature.laximo.domain.use_case.LaximoUseCases
import ru.autopulse05.android.feature.laximo.presentation.unit.util.LaximoUnitEvent
import ru.autopulse05.android.feature.laximo.presentation.unit.util.LaximoUnitState
import ru.autopulse05.android.feature.laximo.presentation.unit.util.LaximoUnitUiEvent
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class LaximoUnitViewModel @Inject constructor(
  application: Application,
  private val laximoUseCases: LaximoUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getUnitJob: Job? = null
  private var getDetailsJob: Job? = null
  private val _uiEventChannel = Channel<LaximoUnitUiEvent>()

  var state by mutableStateOf(LaximoUnitState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getUnit(unitId: String, ssd: String, onSuccess: () -> Unit = {}) {
    getUnitJob?.cancel()

    getUnitJob = laximoUseCases
      .getUnit(
        login = preferencesState.laximoLogin,
        password = preferencesState.laximoPassword,
        locale = preferencesState.locale,
        catalog = state.catalog,
        unitId = unitId,
        ssd = ssd,
      )
      .onEach { data ->
        when (data) {
          is Data.Success ->{
            state = state.copy(unit = data.value)
            onSuccess()
          }
          is Data.Error -> {
            Log.e(TAG, "Error during getting unit: ${data.message}")

            _uiEventChannel.send(LaximoUnitUiEvent.Toast(text = stringResource(R.string.error)))
          }
          is Data.Loading -> state = state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getDetails() {
    getDetailsJob?.cancel()

    getDetailsJob = laximoUseCases
      .getDetails(
        login = preferencesState.laximoLogin,
        password = preferencesState.laximoPassword,
        locale = preferencesState.locale,
        catalog = state.catalog,
        ssd = state.unit!!.ssd,
        unitId = state.unit!!.id
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(details = data.value, isLoading = false)
          is Data.Error -> {
            Log.e(TAG, "Error during getting details: ${data.message}")

            _uiEventChannel.send(LaximoUnitUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
          is Data.Loading -> state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onInitialValuesChange(
    catalog: String,
    unitId: String,
    ssd: String
  ) {
    state = state.copy(
      catalog = catalog
    )

    getUnit(unitId = unitId, ssd = ssd) {
      getDetails()
    }
  }

  private fun onDetailClicked(value: LaximoDetail) {
    viewModelScope.launch {
      _uiEventChannel.send(LaximoUnitUiEvent.OnDetailClicked(value = value))
    }
  }

  init {
    getPreferences()
  }

  fun onEvent(event: LaximoUnitEvent) = when (event) {
    is LaximoUnitEvent.InitialValuesChange -> onInitialValuesChange(
      catalog = event.catalog,
      unitId = event.unitId,
      ssd = event.ssd
    )
    is LaximoUnitEvent.DetailClick -> onDetailClicked(event.value)
  }
}