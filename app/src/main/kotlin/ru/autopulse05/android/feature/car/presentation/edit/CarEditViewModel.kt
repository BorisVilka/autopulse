package ru.autopulse05.android.feature.car.presentation.edit

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
import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.feature.car.domain.use_case.CarUseCases
import ru.autopulse05.android.feature.car.presentation.edit.util.CarEditEvent
import ru.autopulse05.android.feature.car.presentation.edit.util.CarEditState
import ru.autopulse05.android.feature.car.presentation.edit.util.CarEditUiEvent
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.shared.domain.util.Data
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import javax.inject.Inject

@HiltViewModel
class CarEditViewModel @Inject constructor(
  application: Application,
  private val carUseCases: CarUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getYearsJob: Job? = null
  private var getMarksJob: Job? = null
  private var getModelsJob: Job? = null
  private var getModificationsJob: Job? = null
  private var addOrUpdateCarJob: Job? = null
  private val _uiEventChannel = Channel<CarEditUiEvent>()

  var state by mutableStateOf(CarEditState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getYears() {
    getYearsJob?.cancel()

    getYearsJob = carUseCases
      .getYears(
        login = preferencesState.login,
        password = preferencesState.passwordHash
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(
            year = state.year.copy(
              isShowing = true,
              items = data.value
            )
          )
          is Data.Loading -> state.copy(
            year = state.year.copy(
              isShowing = false
            )
          )
          is Data.Error -> state.copy(
            year = state.year.copy(
              isShowing = false
            )
          )
        }

      }
      .launchIn(viewModelScope)
  }

  private fun getMarks() {
    getMarksJob?.cancel()

    getMarksJob = carUseCases
      .getMarks(
        login = preferencesState.login,
        password = preferencesState.passwordHash
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(
            mark = state.mark.copy(
              isShowing = true,
              items = data.value
            )
          )
          is Data.Loading -> state.copy(
            mark = state.mark.copy(
              isShowing = false
            )
          )
          is Data.Error -> state.copy(
            mark = state.mark.copy(
              isShowing = false
            )
          )
        }

      }
      .launchIn(viewModelScope)
  }

  private fun getModels() {
    getModelsJob?.cancel()

    getModelsJob = carUseCases
      .getModels(
        login = preferencesState.login,
        password = preferencesState.passwordHash,
        markId = state.mark.value!!.id!!
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(
            model = state.model.copy(
              isShowing = true,
              items = data.value
            )
          )
          is Data.Loading -> state.copy(
            model = state.model.copy(
              isShowing = false
            )
          )
          is Data.Error -> state.copy(
            model = state.model.copy(
              isShowing = false
            )
          )
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getModifications() {
    getModificationsJob?.cancel()

    getModificationsJob = carUseCases
      .getModifications(
        login = preferencesState.login,
        password = preferencesState.passwordHash,
        markId = state.mark.value!!.id!!,
        modelId = state.model.value!!.id!!
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(
            modification = state.modification.copy(
              isShowing = true,
              items = data.value
            )
          )
          is Data.Loading -> state.copy(
            modification = state.modification.copy(
              isShowing = false
            )
          )
          is Data.Error -> state.copy(
            modification = state.modification.copy(
              isShowing = false
            )
          )
        }

      }
      .launchIn(viewModelScope)
  }

  private fun onYearsVisibilityChanged(value: Boolean) {
    if (value) getYears()
    else state = state.copy(year = state.year.copy(isShowing = false))
  }

  private fun onYearChanged(value: Int) {
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

  private fun onMarksVisibilityChanged(value: Boolean) {
    if (value) getMarks()
    else state = state.copy(mark = state.mark.copy(isShowing = false))
  }

  private fun onMarkChanged(value: CarMark) {
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

  private fun onModelsVisibilityChanged(value: Boolean) {
    if (value) getModels()
    else state = state.copy(model = state.model.copy(isShowing = false))
  }

  private fun onModelChanged(value: CarModel) {
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

  private fun onModificationsVisibilityChanged(value: Boolean) {
    Log.d("TAG","EVENT MODEL $value")
    if (value) getModifications()
    else state = state.copy(modification = state.modification.copy(isShowing = false))
  }

  private fun onModificationChanged(value: CarModification) {
    state = state.copy(
      modification = state.modification.copy(
        isShowing = false,
        value = value
      )
    )
  }

  private fun addToGarage() {
    addOrUpdateCarJob?.cancel()

    addOrUpdateCarJob = carUseCases
      .addToGarage(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash,
        name = state.name.value,
        year = state.year.value!!,
        vin = state.vin.value,
        frame = state.frame.value,
        mileage = state.mileage.value,
        manufacturerId = state.mark.value!!.id,
        modelId = state.model.value!!.id,
        modificationId = state.modification.value!!.id,
        vehicleRegPlate = state.regPlate.value
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> _uiEventChannel.send(CarEditUiEvent.Success)
          is Data.Error -> {
            Log.e(TAG, "Error during saving car: ${data.message}")

            _uiEventChannel.send(CarEditUiEvent.Toast(text = stringResource(R.string.error)))

            state = state.copy(isLoading = false)
          }
          is Data.Loading -> state = state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun updateInGarage() {
    addOrUpdateCarJob?.cancel()

    addOrUpdateCarJob = carUseCases
      .updateInGarage(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash,
        carId = state.carId!!,
        name = state.name.value,
        year = if(state.year.value==null) null else state.year.value!!,
        vin = state.vin.value,
        frame = state.frame.value,
        mileage = state.mileage.value,
        manufacturerId = if(state.mark.value==null) null else state.mark.value!!.id,
        modelId = if(state.model.value==null) null else state.model.value!!.id,
        modificationId = if(state.modification.value==null) null else state.modification.value!!.id,
        vehicleRegPlate = state.regPlate.value,
        comment = state.description.value
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> _uiEventChannel.send(CarEditUiEvent.Success)
          is Data.Error -> {
            Log.e(TAG, "Error during saving car: ${data.message}")

            _uiEventChannel.send(CarEditUiEvent.Toast(text = stringResource(R.string.error)))

            state = state.copy(isLoading = false)
          }
          is Data.Loading -> state = state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onSubmit() {
    if (state.isAlreadyInGarage) {
      updateInGarage()
    } else {
      addToGarage()
    }
  }

  private fun onInitialValuesChange(car: Car?) {
    Log.d("TAG","INIT ${car!=null} ${car?.model} ${car?.mark}")
    if (car != null) {

      state = state.copy(
        carId = car.id,
        name = state.name.copy(
          value = car.name.orEmpty()
        ),
        year = state.year.copy(
          value = car.year
        ),
        vin = state.vin.copy(
          value = car.vin.orEmpty()
        ),
        frame = state.frame.copy(
          value = car.frame.orEmpty()
        ),
        mileage = state.mileage.copy(
          value = car.mileage.orEmpty()
        ),
        mark = state.mark.copy(
          value = car.mark
        ),
        model = state.model.copy(
          value = car.model
        ),
        modification = state.modification.copy(
          value = car.modification
        ),
        regPlate = state.regPlate.copy(
          value = car.vehicleRegPlate.orEmpty()
        ),
        description = state.description.copy(
          value = car.comment.orEmpty()
        )
      )
    }
  }

  override fun onPreferencesChange(preferences: Preferences) {
    super.onPreferencesChange(preferences)

    if (!preferences.isLoggedIn) viewModelScope.launch {
      _uiEventChannel.send(CarEditUiEvent.NotLoggedIn)
    }
  }

  init {
    getPreferences()
  }

  fun onEvent(event: CarEditEvent) = when (event) {
    is CarEditEvent.InitialValuesChange -> onInitialValuesChange(car = event.car)
    is CarEditEvent.TitleChange -> state = state.copy(
      name = state.name.copy(
        value = event.value
      )
    )
    is CarEditEvent.ModeChange -> state = state.copy(
      mode = event.value
    )
    is CarEditEvent.VinCodeChange -> state = state.copy(
      vin = state.vin.copy(
        value = event.value
      ),
      frame = state.frame.copy(
        value = ""
      )
    )
    is CarEditEvent.CascadeNumberChange -> state = state.copy(
      vin = state.vin.copy(
        value = ""
      ),
      frame = state.frame.copy(
        value = event.value
      )
    )
    is CarEditEvent.YearsVisibilityChange -> onYearsVisibilityChanged(
      value = event.value
    )
    is CarEditEvent.YearChange -> onYearChanged(
      value = event.value
    )
    is CarEditEvent.MarksVisibilityChange -> onMarksVisibilityChanged(
      value = event.value
    )
    is CarEditEvent.MarkChange -> onMarkChanged(
      value = event.value
    )
    is CarEditEvent.ModelsVisibilityChange -> onModelsVisibilityChanged(
      value = event.value
    )
    is CarEditEvent.ModelChange -> onModelChanged(
      value = event.value
    )
    is CarEditEvent.ModificationsVisibilityChange -> onModificationsVisibilityChanged(
      value = event.value
    )
    is CarEditEvent.ModificationChange -> onModificationChanged(
      value = event.value
    )
    is CarEditEvent.CountryNumberChange -> state = state.copy(
      regPlate = state.regPlate.copy(
        value = event.value
      )
    )
    is CarEditEvent.MileageChange -> state = state.copy(
      mileage = state.mileage.copy(
        value = event.value
      )
    )
    is CarEditEvent.DescriptionChange -> state = state.copy(
      description = state.description.copy(
        value = event.value
      )
    )
    is CarEditEvent.Submit -> onSubmit()
  }
}