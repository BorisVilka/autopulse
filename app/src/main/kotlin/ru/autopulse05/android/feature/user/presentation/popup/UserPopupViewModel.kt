package ru.autopulse05.android.feature.user.presentation.popup

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.feature.user.domain.use_case.UserUseCases
import ru.autopulse05.android.feature.user.presentation.popup.util.UserPopupEvent
import ru.autopulse05.android.feature.user.presentation.popup.util.UserPopupLinks
import ru.autopulse05.android.feature.user.presentation.popup.util.UserPopupState
import ru.autopulse05.android.feature.user.presentation.popup.util.UserPopupUiEvent
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class UserPopupViewModel @Inject constructor(
  application: Application,
  private val userRepository: UserRepository,
  private val userUseCases: UserUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private val _uiEventChannel = Channel<UserPopupUiEvent>()

  private var logoutJob: Job? = null
  private var getUserJob: Job? = null
  private var updateUserJob: Job? = null

  var state by mutableStateOf(UserPopupState())
  var uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getUser() {
    getUserJob?.cancel()

    getUserJob = userRepository
      .get()
      .onEach { entity -> state = state.copy(user = entity, isLoading = false) }
      .launchIn(viewModelScope)
  }

  private fun updateUser() {
    updateUserJob?.cancel()

    updateUserJob = userUseCases
      .update(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash
      )
      .onEach { data ->
        when(data) {
          is Data.Success -> {}
          is Data.Error -> {
            Log.e(TAG, "Error while updating user info: ${data.message}")

            _uiEventChannel.send(UserPopupUiEvent.Toast(text = stringResource(R.string.error)))

            _uiEventChannel.send(UserPopupUiEvent.Close)

            state = state.copy(isLoading = false)
          }
          is Data.Loading -> state = state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onClose() {
    viewModelScope.launch {
      _uiEventChannel.send(UserPopupUiEvent.Close)
    }
  }

  private fun onSignOut() {
    logoutJob?.cancel()

    logoutJob = userUseCases
      .signOut()
      .onEach { data ->
        when(data) {
          is Data.Success -> _uiEventChannel.send(UserPopupUiEvent.Close)
          is Data.Error -> {
            Log.e(TAG, "Error while logging out: ${data.message}")

            _uiEventChannel.send(UserPopupUiEvent.Toast(text = stringResource(R.string.error)))

            _uiEventChannel.send(UserPopupUiEvent.Close)
          }
          is Data.Loading -> {}
        }
      }
      .launchIn(scope = CoroutineScope(Dispatchers.IO))
  }

  private fun onItemClick(value: UserPopupLinks) {
    onClose()
    viewModelScope.launch {
      _uiEventChannel.send(UserPopupUiEvent.ItemClick(value = value))
    }
  }

  override fun onPreferencesChange(preferences: Preferences) {
    super.onPreferencesChange(preferences)

    if (preferences.isLoggedIn) {
      updateUser()
      getUser()
    } else onClose()
  }

  init {
    getPreferences()
  }

  fun onEvent(event: UserPopupEvent) = when (event) {
    is UserPopupEvent.Close -> onClose()
    is UserPopupEvent.ItemClick -> onItemClick(event.value)
    is UserPopupEvent.SignOut -> onSignOut()
  }
}