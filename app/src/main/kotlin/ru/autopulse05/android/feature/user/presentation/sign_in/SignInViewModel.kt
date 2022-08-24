package ru.autopulse05.android.feature.user.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.user.domain.use_case.UserUseCases
import ru.autopulse05.android.feature.user.presentation.sign_in.util.SignInEvent
import ru.autopulse05.android.feature.user.presentation.sign_in.util.SignInFormState
import ru.autopulse05.android.feature.user.presentation.sign_in.util.SignInUiEvent
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
  private val userUseCases: UserUseCases
) : ViewModel() {

  private val _uiEventChannel = Channel<SignInUiEvent>()

  var formState by mutableStateOf(SignInFormState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onFormSubmit() {

    userUseCases
      .signIn(
        login = formState.login.value,
        password = formState.password.value
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> _uiEventChannel.send(SignInUiEvent.Success)
          is Data.Error -> formState = formState.copy(hasError = true)
          is Data.Loading -> {}
        }
      }
      .launchIn(viewModelScope)

  }

  private fun onNotRegistered() {
    viewModelScope.launch {
      _uiEventChannel.send(SignInUiEvent.SignUp)
    }
  }

  private fun onForgotPassword() {
    viewModelScope.launch {
      _uiEventChannel.send(SignInUiEvent.ForgotPassword)
    }
  }

  fun onEvent(event: SignInEvent): Unit = when (event) {
    is SignInEvent.NotSignedUp -> onNotRegistered()
    is SignInEvent.ForgotPassword -> onForgotPassword()
    is SignInEvent.LoginChange -> formState = formState.copy(
      hasError = false,
      login = formState.login.copy(value = event.value)
    )

    is SignInEvent.PasswordChange -> formState = formState.copy(
      hasError = false,
      password = formState.password.copy(value = event.value)
    )
    is SignInEvent.Submit -> onFormSubmit()
  }
}