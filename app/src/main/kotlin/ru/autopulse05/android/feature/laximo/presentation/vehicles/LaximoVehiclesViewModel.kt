package ru.autopulse05.android.feature.laximo.presentation.vehicles

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle
import ru.autopulse05.android.feature.laximo.domain.use_case.LaximoUseCases
import ru.autopulse05.android.feature.laximo.presentation.vehicles.util.LaximoVehiclesEvent
import ru.autopulse05.android.feature.laximo.presentation.vehicles.util.LaximoVehiclesState
import ru.autopulse05.android.feature.laximo.presentation.vehicles.util.LaximoVehiclesUiEvent
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import javax.inject.Inject

@HiltViewModel
class LaximoVehiclesViewModel @Inject constructor(
  application: Application,
  private val laximoUseCases: LaximoUseCases
) : PreferencesViewModel(application = application) {

  private val _uiEventChannel = Channel<LaximoVehiclesUiEvent>()

  var state by mutableStateOf(LaximoVehiclesState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onVehicleClicked(value: LaximoVehicle) {
    viewModelScope.launch {
      _uiEventChannel.send(LaximoVehiclesUiEvent.GoToCategories(value = value))
    }
  }

  init {
    getPreferences()
  }

  fun onEvent(event: LaximoVehiclesEvent) = when (event) {
    is LaximoVehiclesEvent.OnInitialValuesChanged -> state = state.copy(
      vehicles = event.vehicles
    )
    is LaximoVehiclesEvent.OnVehicleClicked -> onVehicleClicked(event.value)
  }
}