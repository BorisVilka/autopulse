package ru.autopulse05.android.feature.user.presentation.sign_up

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.user.domain.use_case.UserUseCases
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpEvent
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpState
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpTabs
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpUiEvent
import ru.autopulse05.android.shared.domain.use_case.SharedUseCases
import ru.autopulse05.android.shared.domain.util.Data
import ru.autopulse05.android.shared.presentation.BaseViewModel
import ru.autopulse05.android.shared.presentation.util.PresentationText
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
  application: Application,
  private val userUseCases: UserUseCases,
  private val sharedUseCases: SharedUseCases
) : BaseViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private val _uiEventChannel = Channel<SignUpUiEvent>()

  var state by mutableStateOf(SignUpState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onTabChange(value: SignUpTabs) {
    state = SignUpState()

    viewModelScope.launch {
      _uiEventChannel.send(SignUpUiEvent.TabChange(value = value))
    }
  }

  private fun onSubmit(tab: SignUpTabs) {
    val nameResult = sharedUseCases.validateName(
      value = state.name.value
    )
    val phoneResult = sharedUseCases.validatePhone(
      value = state.phone.value
    )
    val emailResult = sharedUseCases.validateEmail(
      value = state.email.value
    )
    val passwordResult = userUseCases.validatePassword(
      value = state.password.value
    )
    val repeatedPasswordResult = userUseCases.validateRepeatedPassword(
      value = state.repeatedPassword.value,
      password = state.password.value
    )
    val cityResult = userUseCases.validateCity(
      value = state.city.value
    )
    val surnameResult = userUseCases.validateSurname(
      value = state.surname.value
    )
    val secondNameResult = userUseCases.validateSecondName(
      value = state.secondName.value
    )
    val actualAddressResult = sharedUseCases.validateAddress(
      value = state.actualAddress.value
    )

    val hasError = listOf(
      nameResult,
      phoneResult,
      emailResult,
      passwordResult,
      repeatedPasswordResult,
      cityResult,
      surnameResult,
      secondNameResult,
      actualAddressResult
    ).any { it is Data.Error }

    if (!hasError) {
      if (state.termsAgreement) {

        val signUpFlow = when (tab) {
          is SignUpTabs.Retail -> userUseCases.signUp(
            name = state.name.value,
            secondName = state.secondName.value,
            surname = state.surname.value,
            mobile = state.phone.value,
            password = state.password.value,
            office = state.office.value!!,
            email = state.email.value,
            city = state.city.value,
            deliveryAddress = state.actualAddress.value
          )
          is SignUpTabs.Wholesale -> userUseCases.signUp(
            name = state.name.value,
            secondName = state.secondName.value,
            surname = state.surname.value,
            password = state.password.value,
            mobile = state.phone.value,
            office = state.office.value!!,
            email = state.email.value,
            icq = state.icq.value,
            regionId = state.region.value!!,
            city = state.city.value,
            organizationName = state.entityName.value,
            organizationForm = state.entityType.value!!,
            organizationOfficialName = state.entityName.value,
            organizationOfficialAddress = state.organizationAddress.value,
            deliveryAddress = state.actualAddress.value
          )
        }

        signUpFlow
          .onEach { data ->
            when (data) {
              is Data.Success -> _uiEventChannel.send(SignUpUiEvent.Success)
              is Data.Error -> {
                Log.e(TAG, "Error during signing up: ${data.message}")

                if (data.message?.contains("уже зарегистрирован") == true) {
                  _uiEventChannel.send(SignUpUiEvent.Toast(text = "Вы уже зарегистрированы"))
                } else {
                  _uiEventChannel.send(SignUpUiEvent.Toast(text = stringResource(R.string.error)))
                }

                state = state.copy(isLoading = false)
              }
              is Data.Loading -> state = state.copy(isLoading = true)
            }
          }
          .launchIn(viewModelScope)
      }
    } else state = state.copy(
      name = state.name.copy(
        error = nameResult.message
      ),
      phone = state.phone.copy(
        error = phoneResult.message
      ),
      email = state.email.copy(
        error = emailResult.message
      ),
      password = state.password.copy(
        error = passwordResult.message
      ),
      repeatedPassword = state.repeatedPassword.copy(
        error = repeatedPasswordResult.message
      ),
      city = state.city.copy(
        error = cityResult.message
      ),
      surname = state.city.copy(
        error = surnameResult.message
      ),
      secondName = state.city.copy(
        error = secondNameResult.message
      ),
      actualAddress = state.city.copy(
        error = actualAddressResult.message
      ),

    )
  }

  fun onEvent(event: SignUpEvent): Unit = when (event) {
    is SignUpEvent.TabChange -> onTabChange(event.value)
    is SignUpEvent.NameChange -> state = state.copy(
      name = state.name.copy(
        value = event.value,
        error = null
      )
    )
    is SignUpEvent.PhoneChange -> state = state.copy(
      phone = state.phone.copy(
        value = event.value,
        error = null
      )
    )
    is SignUpEvent.EmailChange -> state = state.copy(
      email = state.email.copy(
        value = event.value,
        error = null
      )
    )
    is SignUpEvent.PasswordChange -> state = state.copy(
      password = state.password.copy(
        value = event.value,
        error = null
      )
    )
    is SignUpEvent.RepeatedPasswordChange -> state = state.copy(
      repeatedPassword = state.repeatedPassword.copy(
        value = event.value,
        error = null
      )
    )
    is SignUpEvent.OfficeChange -> state = state.copy(
      office = state.office.copy(
        value = event.value,
        isShowing = false
      )
    )
    is SignUpEvent.OfficesVisibilityChange -> state = state.copy(
      office = state.office.copy(isShowing = event.value)
    )
    is SignUpEvent.CityChange -> state = state.copy(
      city = state.city.copy(
        value = event.value,
        error = null
      )
    )
    is SignUpEvent.TermsAgreementChange -> state = state.copy(
      termsAgreement = event.value
    )
    is SignUpEvent.ActualAddressChange -> state = state.copy(
      actualAddress = state.actualAddress.copy(
        value = event.value
      )
    )
    is SignUpEvent.EntityAddressChange -> state = state.copy(
      entityAddress = state.entityAddress.copy(
        value = event.value
      )
    )
    is SignUpEvent.EntityNameChange -> state = state.copy(
      entityName = state.entityName.copy(
        value = event.value
      )
    )
    is SignUpEvent.EntityTypeChange -> state = state.copy(
      entityType = state.entityType.copy(
        value = event.value,
        isShowing = false
      )
    )
    is SignUpEvent.EntityTypesVisibilityChange -> state = state.copy(
      entityType = state.entityType.copy(
        isShowing = event.value
      )
    )
    is SignUpEvent.IcqChange -> state = state.copy(
      icq = state.icq.copy(
        value = event.value
      )
    )
    is SignUpEvent.OrganisationTypeChange -> state = state.copy(
      organisationType = state.organisationType.copy(
        value = event.value,
        isShowing = false
      )
    )
    is SignUpEvent.OrganisationTypesVisibilityChange -> state = state.copy(
      organisationType = state.organisationType.copy(
        isShowing = event.value
      )
    )
    is SignUpEvent.RegionChange -> state = state.copy(
      region = state.region.copy(
        value = event.value,
        isShowing = false
      )
    )
    is SignUpEvent.RegionsVisibilityChange -> state = state.copy(
      region = state.region.copy(
        isShowing = event.value
      )
    )
    is SignUpEvent.RepeatedEmailChange -> state = state.copy(
      repeatedEmail = state.repeatedEmail.copy(
        value = event.value
      )
    )
    is SignUpEvent.Submit -> onSubmit(event.tab)
    is SignUpEvent.SecondNameChange -> state = state.copy(
      secondName = state.secondName.copy(
        value = event.value,
        error = null
      )
    )
    is SignUpEvent.SurnameChange -> state = state.copy(
      surname = state.surname.copy(
        value = event.value,
        error = null
      )
    )
  }
}