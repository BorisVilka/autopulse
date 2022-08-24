package ru.autopulse05.android.feature.user.presentation.sign_up.tabs

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.user.presentation.sign_up.SignUpViewModel
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpEvent
import ru.autopulse05.android.shared.presentation.components.FormDropdownField
import ru.autopulse05.android.shared.presentation.components.FormTextField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun SignUpTabPageRetail(
  viewModel: SignUpViewModel = hiltViewModel()
) {
  val state = viewModel.state

  Column {
    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.surname),
      fieldData = state.surname,
      hint = PresentationText.Resource(id = R.string.surname_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.SurnameChange(value = value))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.name),
      fieldData = state.name,
      hint = PresentationText.Resource(id = R.string.name_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.NameChange(value = value))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.second_name, append = ":"),
      fieldData = state.secondName,
      hint = PresentationText.Resource(id = R.string.second_name),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.SecondNameChange(value = value))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.phone),
      fieldData = state.phone,
      keyboardType = KeyboardType.Phone,
      hint = PresentationText.Resource(id = R.string.phone_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.PhoneChange(value = value))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.email),
      fieldData = state.email,
      keyboardType = KeyboardType.Email,
      hint = PresentationText.Resource(id = R.string.email_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.EmailChange(value = value))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.password),
      fieldData = state.password,
      keyboardType = KeyboardType.Password,
      hint = PresentationText.Resource(id = R.string.password_hint),
      visualTransformation = PasswordVisualTransformation(),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.PasswordChange(value = value))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.repeat_password),
      fieldData = state.repeatedPassword,
      keyboardType = KeyboardType.Password,
      hint = PresentationText.Resource(id = R.string.repeat_password_hint),
      visualTransformation = PasswordVisualTransformation(),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.RepeatedPasswordChange(value = value))
      }
    )

    FormDropdownField(
      fieldName = PresentationText.Resource(id = R.string.nearest_office),
      items = state.office.items,
      selectedName = state.office.value,
      isShowing = state.office.isShowing,
      onMenuClick = {
        viewModel.onEvent(SignUpEvent.OfficesVisibilityChange(value = true))
      },
      onItemClick = { value ->
        viewModel.onEvent(SignUpEvent.OfficeChange(value = state.office.items[value]))
      },
      onDismissRequest = {
        viewModel.onEvent(SignUpEvent.OfficesVisibilityChange(value = false))
      },
      hint = PresentationText.Resource(R.string.nearest_office_hint)
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.city),
      fieldData = state.city,
      hint = PresentationText.Resource(id = R.string.city_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.CityChange(value = value))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.actual_address),
      fieldData = state.actualAddress,
      hint = PresentationText.Resource(id = R.string.actual_address_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.ActualAddressChange(value = value))
      }
    )
  }
}

@Preview(
  name = "Sign Up Tab Page Retail",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun SignUpTabPageRetailPreview() {
  SignUpTabPageRetail()
}