package ru.autopulse05.android.feature.laximo.presentation.vehicle_search

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
import ru.autopulse05.android.feature.laximo.presentation.vehicle_search.util.LaximoVehicleSearchEvent
import ru.autopulse05.android.feature.laximo.presentation.vehicle_search.util.LaximoVehicleSearchState
import ru.autopulse05.android.feature.laximo.presentation.vehicle_search.util.LaximoVehicleSearchUiEvent
import ru.autopulse05.android.feature.settings.presentation.SettingsViewModel
import ru.autopulse05.android.shared.domain.util.Data
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import javax.inject.Inject

@HiltViewModel
class LaximoVehicleSearchViewModel @Inject constructor(
  application: Application,
  private val laximoUseCases: LaximoUseCases
) : SettingsViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getVehiclesJob: Job? = null
  private var getVehicleFormFieldsJob: Job? = null
  private val _uiEventChannel = Channel<LaximoVehicleSearchUiEvent>()

  var state by mutableStateOf(LaximoVehicleSearchState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getVehicles() {
    getVehiclesJob?.cancel()

    getVehiclesJob = laximoUseCases
      .getVehicles(
        login = settingsState.laximoLogin,
        password = settingsState.laximoPassword,
        locale = settingsState.locale,
        catalog = state.catalog,
        ssd = state.ssd
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> _uiEventChannel.send(LaximoVehicleSearchUiEvent.GoToVehicles(vehicles = data.value))
          is Data.Error -> {
            Log.e(TAG, "Error while getting laximo vehicles: ${data.message}")

            _uiEventChannel.send(LaximoVehicleSearchUiEvent.Toast(text = stringResource(R.string.error)))

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
        login = settingsState.laximoLogin,
        password = settingsState.laximoPassword,
        locale = settingsState.locale,
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

            _uiEventChannel.send(LaximoVehicleSearchUiEvent.Toast(text = stringResource(R.string.error)))

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

  private fun onFieldDropdownMenuVisibilityChanged(
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
    getSettings()
  }

  fun onEvent(event: LaximoVehicleSearchEvent) = when (event) {
    is LaximoVehicleSearchEvent.OnInitialValuesChange -> onInitialValuesChange(event.catalog)
    is LaximoVehicleSearchEvent.OnFieldItemsVisibilityChange -> onFieldDropdownMenuVisibilityChanged(
      index = event.index,
      value = event.value
    )
    is LaximoVehicleSearchEvent.Search -> getVehicles()
    is LaximoVehicleSearchEvent.OnFieldValueChange -> onFieldValueChanged(
      index = event.index,
      value = event.value
    )
  }
}