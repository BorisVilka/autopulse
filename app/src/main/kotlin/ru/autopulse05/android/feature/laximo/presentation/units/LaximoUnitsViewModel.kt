package ru.autopulse05.android.feature.laximo.presentation.units

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
import ru.autopulse05.android.feature.laximo.data.remote.dto.LaximoUnitDto
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoUnit
import ru.autopulse05.android.feature.laximo.domain.model.LaximoUnit
import ru.autopulse05.android.feature.laximo.domain.use_case.LaximoUseCases
import ru.autopulse05.android.feature.laximo.presentation.units.util.LaximoUnitsEvent
import ru.autopulse05.android.feature.laximo.presentation.units.util.LaximoUnitsState
import ru.autopulse05.android.feature.laximo.presentation.units.util.LaximoUnitsUiEvent
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class LaximoUnitsViewModel @Inject constructor(
  application: Application,
  private val laximoUseCases: LaximoUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getUnitsJob: Job? = null
  private var getCategoriesJob: Job? = null
  private val _uiEventChannel = Channel<LaximoUnitsUiEvent>()

  var state by mutableStateOf(LaximoUnitsState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getUnits() {
    getCategoriesJob?.cancel()

    getCategoriesJob = laximoUseCases
      .getQuickDetailUseCase(
        login = preferencesState.laximoLogin,
        password = preferencesState.laximoPassword,
        locale = preferencesState.locale,
        catalog = state.catalog,
        quickGroupId = state.categoryId,
        ssd = state.ssd,
        vehicleId = state.vehicleId,
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> {
            Log.d("TAG","SUCCESS")
            var list = mutableListOf<LaximoUnit>()
            for(i in data.value) {
              Log.d("TAG",i.name)
              getUnitsJob?.cancel()
              getUnitsJob = laximoUseCases
                .getUnits(
                login = preferencesState.laximoLogin,
                password = preferencesState.laximoPassword,
                locale = preferencesState.locale,
                catalog = state.catalog,
                ssd = i.ssd,
                vehicleId = state.vehicleId,
                categoryId = i.categoryId.toString()
              ).onEach {
                when(it) {
                  is Data.Success -> {
                    list.addAll(it.value)
                    state = state.copy(units = list.toList(), isLoading = false)
                  }
                }
              }.launchIn(viewModelScope)
            }
            state = state.copy(units = list.toList(), isLoading = false)
          }
          is Data.Error -> {
            Log.e(TAG, "Error during getting units: ${data.message}")

            _uiEventChannel.send(LaximoUnitsUiEvent.Toast(text = stringResource(R.string.error)))

            state = state.copy(isLoading = false)
          }
          is Data.Loading -> state = state.copy(isLoading = true)
        }
        Log.d("TAG","SIZE: "+state.units.size)
      }
      .launchIn(viewModelScope)
  }

  private fun onInitialValuesChanged(
    catalog: String,
    ssd: String,
    vehicleId: String,
    categoryId: String
  ) {
    state = state.copy(
      catalog = catalog,
      ssd = ssd,
      vehicleId = vehicleId,
      categoryId = categoryId
    )

    getUnits()
  }

  private fun onUnitClicked(value: LaximoUnit) {
    viewModelScope.launch {
      _uiEventChannel.send(LaximoUnitsUiEvent.OnUnitClicked(value = value, catalog = state.catalog))
    }
  }

  init {
    getPreferences()
  }

  fun onEvent(event: LaximoUnitsEvent) = when (event) {
    is LaximoUnitsEvent.OnInitialValuesChanged -> onInitialValuesChanged(
      catalog = event.catalog,
      ssd = event.ssd,
      vehicleId = event.vehicleId,
      categoryId = event.categoryId
    )
    is LaximoUnitsEvent.OnUnitClicked -> onUnitClicked(event.value)
  }
}