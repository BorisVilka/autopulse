package ru.autopulse05.android.feature.vin.presentation.parts

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.car.domain.repository.CarRepository
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.vin.domain.use_case.VinUseCases
import ru.autopulse05.android.feature.vin.presentation.parts.util.VinPartsEvent
import ru.autopulse05.android.feature.vin.presentation.parts.util.VinPartsState
import ru.autopulse05.android.feature.vin.presentation.parts.util.VinPartsUiEvent
import ru.autopulse05.android.shared.domain.util.Data
import ru.autopulse05.android.shared.presentation.util.PresentationText
import javax.inject.Inject

@HiltViewModel
class VinPartsViewModel @Inject constructor(
  application: Application,
  private val carRepository: CarRepository,
  private val vinUseCases: VinUseCases,
) : PreferencesViewModel(application = application) {

  private val _uiEventChannel = Channel<VinPartsUiEvent>()

  var state by mutableStateOf(VinPartsState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onNext() {
    val parts = state.parts.value.split("/n").filter { it.isNotEmpty() }
    val partsResult = vinUseCases.validateParts(parts)
    val hasError = listOf(partsResult).any { it is Data.Error }

    if (!hasError) viewModelScope.launch {
      _uiEventChannel.send(
        if (carRepository.count().first() > 0) VinPartsUiEvent.GoToCars(parts = parts)
        else VinPartsUiEvent.GoToCar(parts = parts)
      )
    } else state = state.copy(
      parts = state.parts.copy(
        error = partsResult.message,
      )
    )
  }

  init {
    getPreferences()
  }

  fun onEvent(event: VinPartsEvent) = when (event) {
    is VinPartsEvent.Next -> onNext()
    is VinPartsEvent.PartsChanged -> state = state.copy(
      parts = state.parts.copy(
        value = event.value,
        error = null
      )
    )
  }
}