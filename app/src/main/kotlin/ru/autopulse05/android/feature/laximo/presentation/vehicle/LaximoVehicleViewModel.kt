package ru.autopulse05.android.feature.laximo.presentation.vehicle

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicleFormFieldOption
import ru.autopulse05.android.feature.laximo.domain.use_case.LaximoUseCases
import ru.autopulse05.android.feature.laximo.presentation.vehicle.util.LaximoVehicleEvent
import ru.autopulse05.android.feature.laximo.presentation.vehicle.util.LaximoVehicleState
import ru.autopulse05.android.feature.laximo.presentation.vehicle.util.LaximoVehicleUiEvent
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.shared.domain.util.Data
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import javax.inject.Inject

@HiltViewModel
class LaximoVehicleViewModel @Inject constructor(
  application: Application,
  private val laximoUseCases: LaximoUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getVehiclesJob: Job? = null
  private var getVehicleFormFieldsJob: Job? = null
  private val _uiEventChannel = Channel<LaximoVehicleUiEvent>()

  var state by mutableStateOf(LaximoVehicleState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getVehicles() {
    getVehiclesJob?.cancel()

    getVehiclesJob = laximoUseCases
      .getVehicles(
        login = preferencesState.laximoLogin,
        password = preferencesState.laximoPassword,
        locale = preferencesState.locale,
        catalog = state.catalog,
        ssd = state.ssd
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> _uiEventChannel.send(LaximoVehicleUiEvent.GoToVehicles(vehicles = data.value))
          is Data.Error -> {
            Log.e(TAG, "Error while getting laximo vehicles: ${data.message}")

            _uiEventChannel.send(LaximoVehicleUiEvent.Toast(text = stringResource(R.string.error)))

            state = state.copy(isLoading = false)
          }
          is Data.Loading -> state = state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getVehicleFormFields() {
    getVehicleFormFieldsJob?.cancel()

    getVehicleFormFieldsJob = laximoUseCases
      .getVehicleFormFields(
        login = preferencesState.laximoLogin,
        password = preferencesState.laximoPassword,
        locale = preferencesState.locale,
        catalog = state.catalog,
        ssd = state.ssd
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(
            fields = data.value.map {
              var value = if (it.options.size == 1) it.options[0] else null
              val options = it.options.toMutableList()

              state.fields.firstOrNull { (name, _) -> name == it.name }?.let { (_, field) ->
                if (options.isEmpty()) {
                  value = field.value
                  options.addAll(field.items)
                }
              }

              return@map Pair(
                it.name,
                FormSelectorFieldData(
                  value = value,
                  items = options,
                  isDisabled = options.size <= 1
                )
              )
            }.toPersistentList(),
            isLoading = false
          )
          is Data.Error -> {
            Log.e(TAG, "Error while getting laximo vehicle form fields: ${data.message}")

            _uiEventChannel.send(LaximoVehicleUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
          is Data.Loading -> state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onInitialValuesChange(catalog: String) {
    state = state.copy(catalog = catalog)

    getVehicleFormFields()
  }

  private fun onFieldValueChanged(
    index: Int,
    value: LaximoVehicleFormFieldOption
  ) {
    state = state.copy(
      fields = state.fields.mutate {
        val (name, field) = it[index]
        it[index] = Pair(name, field.copy(value = value))
      },
      ssd = value.key
    )

    getVehicleFormFields()
  }

  private fun onFieldItemsVisibilityChanged(
    index: Int,
    value: Boolean
  ) {
    state = state.copy(
      fields = state.fields.mutate {
        val (name, field) = it[index]
        it[index] = Pair(name, field.copy(isShowing = value))
      }
    )
  }

  init {
    getPreferences()
  }

  fun onEvent(event: LaximoVehicleEvent) = when (event) {
    is LaximoVehicleEvent.InitialValuesChange -> onInitialValuesChange(event.catalog)
    is LaximoVehicleEvent.FieldItemsVisibilityChange -> onFieldItemsVisibilityChanged(
      index = event.index,
      value = event.value
    )
    is LaximoVehicleEvent.FieldValueChange -> onFieldValueChanged(
      index = event.index,
      value = event.value
    )
    is LaximoVehicleEvent.Submit -> getVehicles()
  }
}