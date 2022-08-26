package ru.autopulse05.android.feature.laximo.presentation.catalogs.auto

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
import ru.autopulse05.android.feature.car.domain.use_case.CarUseCases
import ru.autopulse05.android.feature.laximo.domain.model.LaximoCatalog
import ru.autopulse05.android.feature.laximo.domain.use_case.LaximoUseCases
import ru.autopulse05.android.feature.laximo.presentation.catalogs.util.CatalogsEvent
import ru.autopulse05.android.feature.laximo.presentation.catalogs.util.CatalogsState
import ru.autopulse05.android.feature.laximo.presentation.catalogs.util.CatalogsUiEvent
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.vin.domain.use_case.VinUseCases
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class CatalogsViewModel @Inject constructor(
  application: Application,
  private val laximoUseCases: LaximoUseCases,
  private val vinUseCases: VinUseCases,
  private val carUseCases: CarUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getVehiclesJob: Job? = null
  private var getCatalogsJob: Job? = null
  private val _uiEventChannel = Channel<CatalogsUiEvent>()

  var state by mutableStateOf(CatalogsState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getVehiclesByVin() {
    getVehiclesJob?.cancel()

    getVehiclesJob = laximoUseCases
      .getVehiclesByVin(
        login = preferencesState.laximoLogin,
        password = preferencesState.laximoPassword,
        locale = preferencesState.locale,
        vin = state.vin.value
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> _uiEventChannel.send(CatalogsUiEvent.GoToVehicles(vehicles = data.value))
          is Data.Error -> {
            Log.e(TAG, "Error while getting laximo vehicles by vin: ${data.message}")

            _uiEventChannel.send(CatalogsUiEvent.Toast(text = stringResource(R.string.error)))

            state = state.copy(isLoading = false)
          }
          is Data.Loading -> state = state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getVehiclesByFrame() {
    getVehiclesJob?.cancel()

    getVehiclesJob = laximoUseCases
      .getVehiclesByFrame(
        login = preferencesState.laximoLogin,
        password = preferencesState.laximoPassword,
        locale = preferencesState.locale,
        frameName = state.frameName.value,
        frameNumber = state.frameNumber.value
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> _uiEventChannel.send(CatalogsUiEvent.GoToVehicles(vehicles = data.value))
          is Data.Error -> {
            Log.e(TAG, "Error while getting laximo vehicles by frame: ${data.message}")

            _uiEventChannel.send(CatalogsUiEvent.Toast(text = stringResource(R.string.error)))

            state = state.copy(isLoading = false)
          }
          is Data.Loading -> state = state.copy(isLoading = false)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getCatalogs() {
    getCatalogsJob?.cancel()

    getCatalogsJob = laximoUseCases
      .getCatalogs(
        login = preferencesState.laximoLogin,
        password = preferencesState.laximoPassword,
        locale = preferencesState.locale
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(items = data.value, isLoading = false)
          is Data.Loading -> state.copy(isLoading = true)
          is Data.Error -> {
            Log.e(TAG, "Error while getting laximo catalogs: ${data.message}")

            _uiEventChannel.send(CatalogsUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onCatalogClick(value: LaximoCatalog) {
    viewModelScope.launch {
      _uiEventChannel.send(CatalogsUiEvent.GoToVehicleSearch(value = value))
    }
  }

  private fun onSearchByVin() {
    getVehiclesByVin()
  }

  private fun onSearchByFrame() {
    getVehiclesByFrame()
  }

  init {
    getCatalogs()
  }

  fun onEvent(event: CatalogsEvent): Unit = when (event) {
    is CatalogsEvent.SearchByFrame -> onSearchByFrame()
    is CatalogsEvent.SearchByVin -> onSearchByVin()
    is CatalogsEvent.VinChange -> state = state.copy(
      vin = state.vin.copy(value = event.value)
    )
    is CatalogsEvent.CarcaseCodeChange -> state = state.copy(
      frameName = state.frameName.copy(value = event.value)
    )
    is CatalogsEvent.CarcaseNumberChange -> state = state.copy(
      frameNumber = state.frameNumber.copy(value = event.value)
    )
    is CatalogsEvent.OnCatalogClick -> onCatalogClick(event.value)
  }
}