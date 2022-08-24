package ru.autopulse05.android.feature.vin.presentation.car

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.feature.car.domain.use_case.CarUseCases
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.feature.vin.domain.model.CarInfo
import ru.autopulse05.android.feature.vin.domain.model.StockMode
import ru.autopulse05.android.feature.vin.domain.use_case.VinUseCases
import ru.autopulse05.android.feature.vin.presentation.car.util.VinCarEvent
import ru.autopulse05.android.feature.vin.presentation.car.util.VinCarState
import ru.autopulse05.android.feature.vin.presentation.car.util.VinCarUiEvent
import ru.autopulse05.android.shared.domain.util.Data
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import javax.inject.Inject

@HiltViewModel
class VinCarViewModel @Inject constructor(
  application: Application,
  private val carUseCases: CarUseCases,
  private val vinUseCases: VinUseCases,
  private val userRepository: UserRepository
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getYearsJob: Job? = null
  private var getMarksJob: Job? = null
  private var getModelsJob: Job? = null
  private var getModificationsJob: Job? = null
  private val _uiEventChannel = Channel<VinCarUiEvent>()

  var state by mutableStateOf(VinCarState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getSafeLoginAndPasswordHash(): Pair<String, String> =
    if (preferencesState.isLoggedIn) Pair(
      preferencesState.login,
      preferencesState.passwordHash
    ) else Pair(preferencesState.adminLogin, preferencesState.adminPasswordHash)

  private fun onSubmit() {
    viewModelScope.launch {
      if (preferencesState.isLoggedIn) {
        vinUseCases
          .add(
            siteHash = preferencesState.siteHash,
            accessHash = preferencesState.accessHash,
            clientId = preferencesState.clientId,
            vin = state.vin.value,
            frameNumber = state.frameNumber.value,
            model = state.model.value!!,
            modification = state.modification.value!!,
            mark = state.mark.value!!,
            year = state.year.value!!,
            parts = state.parts,
            stockMode = StockMode.Enable,
            user = userRepository.get().first()!!
          )
          .collect { data ->
            when (data) {
              is Data.Success -> _uiEventChannel.send(VinCarUiEvent.GoToStore)
              is Data.Loading -> state = state.copy(isLoading = true)
              is Data.Error -> {
                Log.e(TAG, "Error during adding vin request: ${data.message}")

                _uiEventChannel.send(VinCarUiEvent.Toast(text = stringResource(R.string.error)))

                state = state.copy(isLoading = false)
              }
            }
          }
      } else _uiEventChannel.send(
        VinCarUiEvent.GoToGuest(
          parts = state.parts,
          carInfo = CarInfo(
            brand = state.mark.value!!.name,
            model = state.model.value!!.name,
            year = state.year.value!!,
            modification = state.modification.value!!.name,
            modificationId = state.modification.value!!.id,
            vin = state.vin.value,
            frame = state.frameNumber.value
          )
        )
      )
    }
  }

  private fun onInitialValuesChange(parts: List<String>, car: Car?) {
    state = state.copy(
      mark = state.mark.copy(
        value = car?.mark,
        isDisabled = car == null
      ),
      model = state.model.copy(
        value = car?.model,
      ),
      year = state.year.copy(
        value = car?.year
      ),
      modification = state.modification.copy(
        value = car?.modification,
        isDisabled = car == null
      ),
      parts = parts
    )
  }

  private fun getYears(onSuccess: () -> Unit = {}) {
    val (login, passwordHash) = getSafeLoginAndPasswordHash()

    getYearsJob?.cancel()

    getYearsJob = carUseCases
      .getYears(
        login = login,
        password = passwordHash
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> {
            state = state.copy(year = state.year.copy(items = data.value))
            onSuccess()
          }
          is Data.Loading -> {}
          is Data.Error -> {
            Log.e(TAG, "Error during getting years: ${data.message}")

            _uiEventChannel.send(VinCarUiEvent.Toast(text = stringResource(R.string.error)))
          }
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onYearsVisibilityChange(value: Boolean) {
    if (value) getYears {
      state = state.copy(year = state.year.copy(isShowing = true))
    } else state = state.copy(year = state.year.copy(isShowing = false))
  }

  private fun onYearChange(value: Int) {
    getMarksJob?.cancel()
    getModelsJob?.cancel()
    getModificationsJob?.cancel()

    state = state.copy(
      year = state.year.copy(
        isShowing = false,
        value = value
      ),
      mark = FormSelectorFieldData(),
      model = FormSelectorFieldData(isDisabled = true),
      modification = FormSelectorFieldData(isDisabled = true)
    )
  }

  private fun getMarks(onSuccess: () -> Unit = {}) {
    val (login, passwordHash) = getSafeLoginAndPasswordHash()

    getMarksJob?.cancel()

    getMarksJob = carUseCases
      .getMarks(
        login = login,
        password = passwordHash
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> {
            state = state.copy(mark = state.mark.copy(items = data.value))
            onSuccess()
          }
          is Data.Loading -> {}
          is Data.Error -> {
            Log.e(TAG, "Error during getting marks: ${data.message}")

            _uiEventChannel.send(VinCarUiEvent.Toast(text = stringResource(R.string.error)))
          }
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onMarksVisibilityChange(value: Boolean) {
    if (value) getMarks {
      state = state.copy(mark = state.mark.copy(isShowing = true))
    } else state = state.copy(mark = state.mark.copy(isShowing = false))
  }

  private fun onMarkChange(value: CarMark) {
    getModelsJob?.cancel()
    getModificationsJob?.cancel()

    state = state.copy(
      mark = state.mark.copy(
        isShowing = false,
        value = value
      ),
      model = FormSelectorFieldData(),
      modification = FormSelectorFieldData(isDisabled = true)
    )
  }

  private fun getModels(
    onSuccess: () -> Unit = {}
  ) {
    val (login, passwordHash) = getSafeLoginAndPasswordHash()

    getModelsJob?.cancel()

    getModelsJob = carUseCases
      .getModels(
        login = login,
        password = passwordHash,
        markId = state.mark.value!!.id
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> {
            state = state.copy(model = state.model.copy(items = data.value))
            onSuccess()
          }
          is Data.Loading -> {}
          is Data.Error -> {
            Log.e(TAG, "Error during getting models: ${data.message}")

            _uiEventChannel.send(VinCarUiEvent.Toast(text = stringResource(R.string.error)))
          }
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onModelsVisibilityChange(value: Boolean) {
    if (value) getModels {
      state = state.copy(model = state.model.copy(isShowing = true))
    } else state = state.copy(model = state.model.copy(isShowing = false))
  }

  private fun onModelChange(value: CarModel) {
    getModificationsJob?.cancel()

    state = state.copy(
      model = state.model.copy(
        isShowing = false,
        value = value
      ),
      modification = state.modification.copy(
        value = null,
        isDisabled = false
      )
    )
  }

  private fun getModifications(
    onSuccess: () -> Unit = {}
  ) {
    val (login, passwordHash) = getSafeLoginAndPasswordHash()

    getModificationsJob?.cancel()

    getModificationsJob = carUseCases
      .getModifications(
        login = login,
        password = passwordHash,
        markId = state.mark.value!!.id,
        modelId = state.model.value!!.id
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> {
            state = state.copy(modification = state.modification.copy(items = data.value))
            onSuccess()
          }
          is Data.Loading -> {}
          is Data.Error -> {
            Log.e(TAG, "Error during getting modifications: ${data.message}")

            _uiEventChannel.send(VinCarUiEvent.Toast(text = stringResource(R.string.error)))
          }
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onModificationsVisibilityChange(value: Boolean) {
    if (value) getModifications {
      state = state.copy(modification = state.modification.copy(isShowing = true))
    } else state = state.copy(modification = state.modification.copy(isShowing = false))
  }

  private fun onModificationChange(value: CarModification) {
    state = state.copy(
      modification = state.modification.copy(
        isShowing = false,
        value = value
      )
    )
  }

  init {
    getPreferences()
  }

  fun onEvent(event: VinCarEvent) = when (event) {
    is VinCarEvent.InitialValuesChange -> onInitialValuesChange(
      parts = event.parts,
      car = event.car
    )
    is VinCarEvent.AdvancedParametersVisibilityChange -> state = state.copy(
      isAdvancedParametersVisible = event.value
    )
    is VinCarEvent.FrameNumberChange -> state = state.copy(
      frameNumber = state.frameNumber.copy(value = event.value)
    )
    is VinCarEvent.ModeChange -> state = state.copy(mode = event.value)
    is VinCarEvent.VinChange -> state = state.copy(
      vin = state.vin.copy(value = event.value)
    )
    is VinCarEvent.MarkChange -> onMarkChange(event.value)
    is VinCarEvent.MarksVisibilityChange -> onMarksVisibilityChange(event.value)
    is VinCarEvent.ModelChange -> onModelChange(event.value)
    is VinCarEvent.ModelsVisibilityChange -> onModelsVisibilityChange(event.value)
    is VinCarEvent.ModificationChange -> onModificationChange(event.value)
    is VinCarEvent.ModificationsVisibilityChange -> onModificationsVisibilityChange(event.value)
    is VinCarEvent.YearChange -> onYearChange(event.value)
    is VinCarEvent.YearsVisibilityChange -> onYearsVisibilityChange(event.value)
    is VinCarEvent.Submit -> onSubmit()
  }
}