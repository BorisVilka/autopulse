package ru.autopulse05.android.feature.user.presentation.profile

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
import ru.autopulse05.android.feature.laximo.domain.use_case.LaximoUseCases
import ru.autopulse05.android.feature.laximo.presentation.catalogs.CatalogsViewModel
import ru.autopulse05.android.feature.laximo.presentation.catalogs.util.CatalogsUiEvent
import ru.autopulse05.android.feature.order.domain.model.Order
import ru.autopulse05.android.feature.order.domain.use_case.OrderUseCases
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.feature.user.domain.use_case.UserUseCases
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileEvent
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileState
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabs
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileUiEvent
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
  application: Application,
  private val userRepository: UserRepository,
  private val carRepository: CarRepository,
  private val orderUseCases: OrderUseCases,
  private val userUseCases: UserUseCases,
  private val carUseCases: CarUseCases,
  private var laximoUseCases: LaximoUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getUserJob: Job? = null
  private var getGarageJob: Job? = null
  private var tabDataJob: Job? = null
  private var getVehiclesJob: Job? = null
  private val _uiEventChannel = Channel<ProfileUiEvent>()

  var state by mutableStateOf(ProfileState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  override fun onPreferencesChange(preferences: Preferences) {
    super.onPreferencesChange(preferences)

    if (!preferences.isLoggedIn) viewModelScope.launch {
      _uiEventChannel.send(ProfileUiEvent.NotSignedIn)
    }
  }

  init {
    getPreferences()
  }

  private fun getUser() {
    getUserJob?.cancel()

    getUserJob = userRepository
      .get()
      .onEach { entity ->
        state = state.copy(user = entity)
      }
      .launchIn(viewModelScope)
  }

  private fun getGarage() {
    getGarageJob?.cancel()

    getGarageJob = carRepository
      .getAll()
      .onEach { entities -> state = state.copy(cars = entities) }
      .launchIn(viewModelScope)
  }

  private fun getOrders() {
    tabDataJob?.cancel()

    tabDataJob = orderUseCases
      .getAll(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(
            orders = data.value,
            isLoading = false,
            isNotFound = false
          )
          is Data.Error -> {
            if (data.code == 404) state.copy(
              isNotFound = true,
              isLoading = false
            ) else {
              Log.e(TAG, "Error during getting orders: ${data.message}")

              _uiEventChannel.send(ProfileUiEvent.Toast(text = stringResource(R.string.error)))

              state.copy(isLoading = false)
            }
          }
          is Data.Loading -> state.copy(isLoading = true, isNotFound = false)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getPickings() {
    tabDataJob?.cancel()

    tabDataJob = orderUseCases
      .getPickings(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash,
        opId = "" // TODO
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(
            pickings = data.value,
            isLoading = false,
            isNotFound = false
          )
          is Data.Error -> {
            Log.e(TAG, "Error during getting pickings: ${data.message}")

            _uiEventChannel.send(ProfileUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false, isNotFound = false)
          }
          is Data.Loading -> state.copy(isLoading = true, isNotFound = false)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getRefunds() {
    tabDataJob?.cancel()

    tabDataJob = orderUseCases
      .getRefunds(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash,
        opId = "", // TODO
        status = 0,
        type = 0
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(
            refunds = data.value,
            isLoading = false,
            isNotFound = false
          )
          is Data.Error -> {
            Log.e(TAG, "Error during getting refunds: ${data.message}")

            _uiEventChannel.send(ProfileUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false, isNotFound = false)
          }
          is Data.Loading -> state.copy(isLoading = true, isNotFound = false)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun updateGarage() {
    tabDataJob?.cancel()

    tabDataJob = carUseCases
      .updateGarage(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(isLoading = false)
          is Data.Error -> {
            Log.e(TAG, "Error during updating garage: ${data.message}")

            _uiEventChannel.send(ProfileUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
          is Data.Loading -> state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun updateUser() {
    tabDataJob?.cancel()

    tabDataJob = userUseCases
      .update(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> state.copy(isLoading = false, isNotFound = false)
          is Data.Error -> {
            Log.e(TAG, "Error during updating user: ${data.message}")

            _uiEventChannel.send(ProfileUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false, isNotFound = false)
          }
          is Data.Loading -> state.copy(isLoading = true, isNotFound = false)
        }
      }
      .launchIn(viewModelScope)
  }


  private fun onAddCar() {
    viewModelScope.launch {
      _uiEventChannel.send(ProfileUiEvent.AddCar)
    }
  }

  private fun onTabChange(value: ProfileTabs) {
    if (preferencesState.isLoggedIn) when (value) {
      ProfileTabs.Orders -> getOrders()
      ProfileTabs.Pickings -> getPickings()
      ProfileTabs.Refunds -> getRefunds()
      ProfileTabs.Garage -> {
        updateGarage()
        getGarage()
      }
      ProfileTabs.Data -> {
        updateUser()
        getUser()
      }
    }

    viewModelScope.launch {
      _uiEventChannel.send(ProfileUiEvent.TabChange(value = value))
    }
  }

  fun editCar(value: Car) {
    viewModelScope.launch {
      _uiEventChannel.send(ProfileUiEvent.EditCar(value = value))
    }
  }

  fun openVinRequest() {
    viewModelScope.launch {
      _uiEventChannel.send(ProfileUiEvent.OpenVinRequest)
    }
  }

  private fun orderDetail(value: Order) {
    viewModelScope.launch {
      _uiEventChannel.send(ProfileUiEvent.OrderDetail(value = value))
    }
  }

  fun getVehiclesByVin(vin: String) {
    getVehiclesJob?.cancel()

    getVehiclesJob = laximoUseCases
      .getVehiclesByVin(
        login = preferencesState.laximoLogin,
        password = preferencesState.laximoPassword,
        locale = preferencesState.locale,
        vin = vin
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> _uiEventChannel.send(ProfileUiEvent.GoToVehicles(vehicles = data.value))
          is Data.Error -> {
            Log.e("TAG", "Error while getting laximo vehicles by vin: ${data.message}")

            _uiEventChannel.send(ProfileUiEvent.Toast(text = stringResource(R.string.error)))

            state = state.copy(isLoading = false)
          }
          is Data.Loading -> state = state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  fun onEvent(event: ProfileEvent): Unit = when (event) {
    is ProfileEvent.TabChange -> onTabChange(event.value)
    is ProfileEvent.AddCar -> onAddCar()
    is ProfileEvent.OrderDetail -> orderDetail(event.value)
  }
}