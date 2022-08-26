package ru.autopulse05.android.feature.garage.presentation.add

import android.app.Application
import android.widget.Toast
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
import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.feature.car.domain.use_case.CarUseCases
import ru.autopulse05.android.feature.car.presentation.edit.util.CarEditUiEvent
import ru.autopulse05.android.feature.garage.domain.use_case.GarageUseCases
import ru.autopulse05.android.feature.garage.presentation.add.util.AddToGarageEvent
import ru.autopulse05.android.feature.garage.presentation.add.util.AddToGarageFormState
import ru.autopulse05.android.feature.garage.presentation.add.util.AddToGarageUiEvent
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.settings.presentation.Settings
import ru.autopulse05.android.feature.settings.presentation.SettingsViewModel
import ru.autopulse05.android.shared.domain.util.Data
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import javax.inject.Inject

@HiltViewModel
class AddToGarageViewModel @Inject constructor(
  application: Application,
  private val carUseCases: CarUseCases,
  private val garageUseCases: GarageUseCases
) : PreferencesViewModel(application = application) {

  private var getYearsJob: Job? = null
  private var getMarksJob: Job? = null
  private var getModelsJob: Job? = null
  private var getModificationsJob: Job? = null
  private var addCarJob: Job? = null
  private val _uiEventChannel = Channel<AddToGarageUiEvent>()

  var formState by mutableStateOf(AddToGarageFormState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  override fun onPreferencesChange(preferences: Preferences) {
    super.onPreferencesChange(preferences)

    if (!preferences.isLoggedIn) viewModelScope.launch {
      _uiEventChannel.send(AddToGarageUiEvent.NotLoggedIn)
    }
  }

  init {
    getPreferences()
  }

  private fun onYearsVisibilityChanged(value: Boolean) {
    getYearsJob?.cancel()

    if (value) {
      getYearsJob = carUseCases
        .getYears(
          login = preferencesState.login,
          password = preferencesState.passwordHash
        )
        .onEach { data ->
          formState = when (data) {
            is Data.Success -> formState.copy(
              year = formState.year.copy(
                isShowing = true,
                items = data.value
              )
            )
            is Data.Loading -> formState.copy(
              year = formState.year.copy(
                isShowing = false
              )
            )
            is Data.Error -> formState.copy(
              year = formState.year.copy(
                isShowing = false
              )
            )
          }

        }
        .launchIn(viewModelScope)
    } else formState = formState.copy(year = formState.year.copy(isShowing = false))
  }

  private fun onYearChanged(value: Int) {
    getMarksJob?.cancel()
    getModelsJob?.cancel()
    getModificationsJob?.cancel()

    formState = formState.copy(
      year = formState.year.copy(
        isShowing = false,
        value = value
      ),
      mark = FormSelectorFieldData(),
      model = FormSelectorFieldData(isDisabled = true),
      modification = FormSelectorFieldData(isDisabled = true)
    )
  }

  private fun onMarksVisibilityChanged(value: Boolean) {
    getMarksJob?.cancel()

    if (value) {
      getMarksJob = carUseCases
        .getMarks(
          login = preferencesState.login,
          password = preferencesState.passwordHash
        )
        .onEach { data ->
          formState = when (data) {
            is Data.Success -> formState.copy(
              mark = formState.mark.copy(
                isShowing = true,
                items = data.value
              )
            )
            is Data.Loading -> formState.copy(
              mark = formState.mark.copy(
                isShowing = false
              )
            )
            is Data.Error -> formState.copy(
              mark = formState.mark.copy(
                isShowing = false
              )
            )
          }

        }
        .launchIn(viewModelScope)
    } else formState = formState.copy(mark = formState.mark.copy(isShowing = false))
  }

  private fun onMarkChanged(value: CarMark) {
    getModelsJob?.cancel()
    getModificationsJob?.cancel()

    formState = formState.copy(
      mark = formState.mark.copy(
        isShowing = false,
        value = value
      ),
      model = FormSelectorFieldData(),
      modification = FormSelectorFieldData(isDisabled = true)
    )
  }

  private fun onModelsVisibilityChanged(value: Boolean) {
    getModelsJob?.cancel()

    if (value) {
      getModelsJob = carUseCases
        .getModels(
          login = preferencesState.login,
          password = preferencesState.passwordHash,
          markId = formState.mark.value!!.id!!
        )
        .onEach { data ->
          formState = when (data) {
            is Data.Success -> formState.copy(
              model = formState.model.copy(
                isShowing = true,
                items = data.value
              )
            )
            is Data.Loading -> formState.copy(
              model = formState.model.copy(
                isShowing = false
              )
            )
            is Data.Error -> formState.copy(
              model = formState.model.copy(
                isShowing = false
              )
            )
          }

        }
        .launchIn(viewModelScope)
    } else formState = formState.copy(model = formState.model.copy(isShowing = false))
  }

  private fun onModelChanged(value: CarModel) {
    getModificationsJob?.cancel()

    formState = formState.copy(
      model = formState.model.copy(
        isShowing = false,
        value = value
      ),
      modification = formState.modification.copy(
        value = null,
        isDisabled = false
      )
    )
  }

  private fun onModificationsVisibilityChanged(value: Boolean) {
    getModificationsJob?.cancel()

    if (value) {
      getModificationsJob = carUseCases
        .getModifications(
          login = preferencesState.login,
          password = preferencesState.passwordHash,
          markId = formState.mark.value!!.id!!,
          modelId = formState.model.value!!.id!!
        )
        .onEach { data ->
          formState = when (data) {
            is Data.Success -> formState.copy(
              modification = formState.modification.copy(
                isShowing = true,
                items = data.value
              )
            )
            is Data.Loading -> formState.copy(
              modification = formState.modification.copy(
                isShowing = false
              )
            )
            is Data.Error -> formState.copy(
              modification = formState.modification.copy(
                isShowing = false
              )
            )
          }

        }
        .launchIn(viewModelScope)
    } else formState = formState.copy(modification = formState.modification.copy(isShowing = false))
  }

  private fun onModificationChanged(value: CarModification) {
    formState = formState.copy(
      modification = formState.modification.copy(
        isShowing = false,
        value = value
      )
    )
  }

  private fun onSubmit() {
    addCarJob?.cancel()

    addCarJob = garageUseCases
      .addCar(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash,
        name = formState.name.value,
        year = formState.year.value,
        vin = formState.vinCode.value,
        frame = formState.frame.value,
        mileage = formState.mileage.value,
        manufacturerId = if(formState.mark.value==null) null else formState.mark.value!!.id,
        modelId = if(formState.model.value==null) null else formState.model.value!!.id,
        modificationId = if(formState.modification.value==null) null else formState.modification.value!!.id,
        vehicleRegPlate = formState.regPlate.value,
        comment = formState.description.value
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> _uiEventChannel.send(AddToGarageUiEvent.Success)
          is Data.Error -> {
              Toast.makeText(context,"Произошла ошибка при добавлении машины, попробуйте проверить введённые данные",Toast.LENGTH_LONG).show()
          }
          is Data.Loading -> {}
        }
      }
      .launchIn(viewModelScope)
  }

  fun onEvent(event: AddToGarageEvent) = when (event) {
    is AddToGarageEvent.TitleChanged -> formState = formState.copy(
      name = formState.name.copy(
        value = event.value
      )
    )
    is AddToGarageEvent.ModeChanged -> formState = formState.copy(
      mode = event.value
    )
    is AddToGarageEvent.VinCodeChanged -> formState = formState.copy(
      vinCode = formState.vinCode.copy(
        value = event.value
      ),
      frame = formState.frame.copy(
        value = ""
      )
    )
    is AddToGarageEvent.CascadeNumberChanged -> formState = formState.copy(
      vinCode = formState.vinCode.copy(
        value = ""
      ),
      frame = formState.frame.copy(
        value = event.value
      )
    )
    is AddToGarageEvent.YearsVisibilityChanged -> onYearsVisibilityChanged(
      value = event.value
    )
    is AddToGarageEvent.YearChanged -> onYearChanged(
      value = event.value
    )
    is AddToGarageEvent.MarksVisibilityChanged -> onMarksVisibilityChanged(
      value = event.value
    )
    is AddToGarageEvent.MarkChanged -> onMarkChanged(
      value = event.value
    )
    is AddToGarageEvent.ModelsVisibilityChanged -> onModelsVisibilityChanged(
      value = event.value
    )
    is AddToGarageEvent.ModelChanged -> onModelChanged(
      value = event.value
    )
    is AddToGarageEvent.ModificationsVisibilityChanged -> onModificationsVisibilityChanged(
      value = event.value
    )
    is AddToGarageEvent.ModificationChanged -> onModificationChanged(
      value = event.value
    )
    is AddToGarageEvent.CountryNumberChanged -> formState = formState.copy(
      regPlate = formState.regPlate.copy(
        value = event.value
      )
    )
    is AddToGarageEvent.MileageChanged -> formState = formState.copy(
      mileage = formState.mileage.copy(
        value = event.value
      )
    )
    is AddToGarageEvent.DescriptionChanged -> formState = formState.copy(
      description = formState.description.copy(
        value = event.value
      )
    )
    is AddToGarageEvent.Submit -> onSubmit()
  }
}