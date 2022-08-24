package ru.autopulse05.android.feature.vin.presentation.cars

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
import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.car.domain.repository.CarRepository
import ru.autopulse05.android.feature.car.domain.use_case.CarUseCases
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.vin.presentation.cars.util.VinCarsEvent
import ru.autopulse05.android.feature.vin.presentation.cars.util.VinCarsState
import ru.autopulse05.android.feature.vin.presentation.cars.util.VinCarsUiEvent
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class VinCarsViewModel @Inject constructor(
  application: Application,
  private val carUseCases: CarUseCases,
  private val carRepository: CarRepository
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private val _uiEventChannel = Channel<VinCarsUiEvent>()
  private var getGarageJob: Job? = null
  private var updateGarageJob: Job? = null

  var state by mutableStateOf(VinCarsState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getGarage() {
    getGarageJob?.cancel()

    getGarageJob = carRepository
      .getAll()
      .onEach { entities -> state = state.copy(cars = entities) }
      .launchIn(viewModelScope)
  }

  private fun updateGarage() {
    updateGarageJob?.cancel()

    updateGarageJob = carUseCases
      .updateGarage(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(isLoading = false)
          is Data.Error -> {
            Log.e(TAG, "Error during updating garage: ${data.message}")

            _uiEventChannel.send(VinCarsUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
          is Data.Loading -> state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onSelectCar(value: Car) {
    viewModelScope.launch {
      _uiEventChannel.send(VinCarsUiEvent.SelectCar(parts = state.parts, car = value))
    }
  }

  private fun onOtherCar() {
    viewModelScope.launch {
      _uiEventChannel.send(VinCarsUiEvent.OtherCar(state.parts))
    }
  }

  private fun onInitialValuesChange(parts: List<String>) {
    state = state.copy(
      parts = parts
    )
  }

  override fun onPreferencesChange(preferences: Preferences) {
    super.onPreferencesChange(preferences)

    if (preferences.isLoggedIn) {
      updateGarage()
      getGarage()
    } else onOtherCar()
  }

  init {
    getPreferences()
  }

  fun onEvent(event: VinCarsEvent) = when(event) {
    is VinCarsEvent.OtherCar -> onOtherCar()
    is VinCarsEvent.CarClick -> onSelectCar(event.value)
    is VinCarsEvent.InitialValuesChange -> onInitialValuesChange(event.parts)
  }
}