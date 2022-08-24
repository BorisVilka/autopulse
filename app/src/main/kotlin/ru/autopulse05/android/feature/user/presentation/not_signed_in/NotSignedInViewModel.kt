package ru.autopulse05.android.feature.user.presentation.not_signed_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.user.presentation.not_signed_in.util.NotSignedInEvent
import ru.autopulse05.android.feature.user.presentation.not_signed_in.util.NotSignedInUiEvent
import javax.inject.Inject

@HiltViewModel
class NotSignedInViewModel @Inject constructor() : ViewModel() {

  private val _uiEventChannel = Channel<NotSignedInUiEvent>()

  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onSignIn() {
    viewModelScope.launch {
      _uiEventChannel.send(NotSignedInUiEvent.SignIn)
    }
  }

  private fun onSignUp() {
    viewModelScope.launch {
      _uiEventChannel.send(NotSignedInUiEvent.SignUp)
    }
  }

  fun onEvent(event: NotSignedInEvent): Unit = when (event) {
    is NotSignedInEvent.SignUp -> onSignUp()
    is NotSignedInEvent.SignIn -> onSignIn()
  }
}