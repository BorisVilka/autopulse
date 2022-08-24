package ru.autopulse05.android.feature.user.presentation.new_password

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
import ru.autopulse05.android.feature.user.domain.use_case.UserUseCases
import ru.autopulse05.android.feature.user.presentation.new_password.util.NewPasswordEvent
import ru.autopulse05.android.feature.user.presentation.new_password.util.NewPasswordFormState
import ru.autopulse05.android.feature.user.presentation.new_password.util.NewPasswordUiEvent
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class NewPasswordViewModel @Inject constructor(
  private val userUseCases: UserUseCases
) : ViewModel() {

  private val _uiEventChannel = Channel<NewPasswordUiEvent>()

  var formState by mutableStateOf(NewPasswordFormState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onFormSubmit() {
    val newPasswordResult = userUseCases.validatePassword(
      value = formState.newPassword.value
    )

    val hasError = listOf(
      newPasswordResult
    ).any { it is Data.Error }

    if (!hasError) userUseCases
        .restorePassword(
          phone = formState.phone.value,
          newPassword = formState.phone.value,
          code = formState.code.value
        )
        .onEach {
          when (it) {
            is Data.Success -> {
              userUseCases.signIn(
                login = formState.phone.value,
                password = formState.newPassword.value
              )

              _uiEventChannel.send(NewPasswordUiEvent.Success)
            }
            is Data.Error -> formState = formState.copy(hasError = true)
            is Data.Loading -> {}
          }
        }
      .launchIn(viewModelScope)
    else formState = formState.copy(hasError = true)
  }

  fun onEvent(event: NewPasswordEvent): Unit = when (event) {
    is NewPasswordEvent.PhoneChanged -> formState = formState.copy(
      hasError = false,
      phone = formState.phone.copy(value = event.value)
    )
    is NewPasswordEvent.CodeChanged -> formState = formState.copy(
      hasError = false,
      code = formState.code.copy(value = event.value)
    )
    is NewPasswordEvent.NewPasswordChanged -> formState = formState.copy(
      hasError = false,
      newPassword = formState.newPassword.copy(value = event.value)
    )
    is NewPasswordEvent.Submit -> onFormSubmit()
  }
}