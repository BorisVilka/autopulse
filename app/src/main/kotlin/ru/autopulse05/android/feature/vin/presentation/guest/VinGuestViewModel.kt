package ru.autopulse05.android.feature.vin.presentation.guest

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
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.feature.user.domain.use_case.UserUseCases
import ru.autopulse05.android.feature.vin.domain.model.CarInfo
import ru.autopulse05.android.feature.vin.domain.model.GuestInfo
import ru.autopulse05.android.feature.vin.domain.model.StockMode
import ru.autopulse05.android.feature.vin.domain.use_case.VinUseCases
import ru.autopulse05.android.feature.vin.presentation.guest.util.VinGuestEvent
import ru.autopulse05.android.feature.vin.presentation.guest.util.VinGuestState
import ru.autopulse05.android.feature.vin.presentation.guest.util.VinGuestUiEvent
import ru.autopulse05.android.shared.domain.use_case.SharedUseCases
import ru.autopulse05.android.shared.domain.util.Data
import ru.autopulse05.android.shared.presentation.util.PresentationText
import javax.inject.Inject

@HiltViewModel
class VinGuestViewModel @Inject constructor(
  application: Application,
  private val vinUseCases: VinUseCases,
  private val userUseCases: UserUseCases,
  private val userRepository: UserRepository,
  private val sharedUseCases: SharedUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }
  private var getUserJob: Job? = null
  private val _uiEventChannel = Channel<VinGuestUiEvent>()

  var state by mutableStateOf(VinGuestState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onInitialValuesChange(
    carInfo: CarInfo,
    parts: List<String>
  ) {
    state = state.copy(
      carInfo = carInfo,
      parts = parts
    )
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
  private fun onSubmit() {
    val nameResult = sharedUseCases.validateName(
      value = state.name.value
    )
    val phoneResult = sharedUseCases.validatePhone(
      value = state.phone.value
    )
    val emailResult = sharedUseCases.validateEmail(
      value = state.email.value
    )

    val hasError = listOf(
      nameResult,
      phoneResult,
      emailResult
    ).any { it is Data.Error }

    if (!hasError) if (state.termsAgreement) {
      vinUseCases
        .add(
          siteHash = preferencesState.siteHash,
          accessHash = preferencesState.accessHash,
          clientId = state.user!!.id,
          carInfo = state.carInfo!!,
          parts = state.parts,
          stockMode = StockMode.Enable,
          guestInfo = GuestInfo(
            name = state.name.value,
            phone = state.phone.value,
            email = state.email.value,
          ),
          comment = null
        )
        .onEach { data ->
          when (data) {
            is Data.Error -> {
              Log.e(TAG, "Error while adding adding vin request: ${data.message}")

              _uiEventChannel.send(VinGuestUiEvent.Toast(text = stringResource(R.string.error)))

              state = state.copy(isLoading = false)
            }
            is Data.Loading -> state = state.copy(isLoading = true)
            is Data.Success -> _uiEventChannel.send(VinGuestUiEvent.GoToStore)
          }
        }
        .launchIn(viewModelScope)
    } else state = state.copy(
      name = state.name.copy(
        error = nameResult.message,
      ),
      phone = state.phone.copy(
        error = phoneResult.message,
      ),
      email = state.email.copy(
        error = emailResult.message,
      )
    )
  }

  init {
    getPreferences()
    getUser()
  }

  fun onEvent(event: VinGuestEvent) = when (event) {
    is VinGuestEvent.InitialValuesChange -> onInitialValuesChange(
      carInfo = event.carInfo,
      parts = event.parts
    )
    is VinGuestEvent.Submit -> onSubmit()
    is VinGuestEvent.EmailChange -> state = state.copy(
      email = state.email.copy(
        value = event.value,
        error = null
      )
    )
    is VinGuestEvent.NameChange -> state = state.copy(
      name = state.name.copy(
        value = event.value,
        error = null
      )
    )
    is VinGuestEvent.PhoneChange -> state = state.copy(
      phone = state.phone.copy(
        value = event.value,
        error = null
      )
    )
    is VinGuestEvent.TermsAgreementChange -> state = state.copy(
      termsAgreement = event.value
    )
  }
}