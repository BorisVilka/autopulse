package ru.autopulse05.android.feature.user.presentation.restore

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
import ru.autopulse05.android.feature.user.presentation.restore.util.RestoreEvent
import ru.autopulse05.android.feature.user.presentation.restore.util.RestoreFormState
import ru.autopulse05.android.feature.user.presentation.restore.util.RestoreUiEvent
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class RestoreViewModel @Inject constructor(
  private val userUseCases: UserUseCases
) : ViewModel() {

  private val _uiEventChannel = Channel<RestoreUiEvent>()

  var formState by mutableStateOf(RestoreFormState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onSubmit() {
    userUseCases
      .sendRestoreCode(
        emailOrPhone = formState.emailOrMobile.value
      )
      .onEach { data ->
        when (data) {
          is Data.Success -> _uiEventChannel.send(RestoreUiEvent.Success)
          is Data.Loading -> {}
          is Data.Error -> {}
        }
      }
      .launchIn(viewModelScope)
  }

  fun onEvent(event: RestoreEvent): Unit = when (event) {
    is RestoreEvent.EmailOrMobileChanged -> formState = formState.copy(
      hasError = false,
      emailOrMobile = formState.emailOrMobile.copy(value = event.value)
    )
    is RestoreEvent.Submit -> onSubmit()
  }
}