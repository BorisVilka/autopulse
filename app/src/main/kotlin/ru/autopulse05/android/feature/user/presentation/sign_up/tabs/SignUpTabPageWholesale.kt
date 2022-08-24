package ru.autopulse05.android.feature.user.presentation.sign_up.tabs

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.user.presentation.sign_up.SignUpViewModel
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpEvent
import ru.autopulse05.android.shared.presentation.components.FormDropdownField
import ru.autopulse05.android.shared.presentation.components.FormTextField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun SignUpTabPageWholesale(
  viewModel: SignUpViewModel = hiltViewModel()
) {
  val state = viewModel.state

  Column {
    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.organization_name),
      fieldData = state.name,
      hint = PresentationText.Resource(id = R.string.organization_name_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.NameChange(value = value))
      }
    )

    FormDropdownField(
      fieldName = PresentationText.Resource(id = R.string.region),
      items = state.region.items,
      selectedName = state.region.value,
      isShowing = state.region.isShowing,
      onMenuClick = {
        viewModel.onEvent(SignUpEvent.RegionsVisibilityChange(value = true))
      },
      onItemClick = { index ->
        viewModel.onEvent(SignUpEvent.RegionChange(value = state.region.items[index]))
      },
      onDismissRequest = {
        viewModel.onEvent(SignUpEvent.RegionsVisibilityChange(value = false))
      },
      hint = PresentationText.Resource(R.string.region_hint)
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.city),
      fieldData = state.city,
      hint = PresentationText.Resource(id = R.string.city_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.CityChange(value = value))
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
      onItemClick = { index ->
        viewModel.onEvent(SignUpEvent.OfficeChange(value = state.office.items[index]))
      },
      onDismissRequest = {
        viewModel.onEvent(SignUpEvent.OfficesVisibilityChange(value = false))
      },
      hint = PresentationText.Resource(id = R.string.nearest_office_hint)
    )

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
      fieldName = PresentationText.Resource(id = R.string.repeat_email),
      fieldData = state.repeatedEmail,
      keyboardType = KeyboardType.Email,
      hint = PresentationText.Resource(id = R.string.repeat_email_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.RepeatedEmailChange(value = value))
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

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.icq),
      fieldData = state.icq,
      hint = PresentationText.Resource(id = R.string.icq_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.IcqChange(value = value))
      }
    )

    FormDropdownField(
      fieldName = PresentationText.Resource(id = R.string.organization_type),
      items = state.organisationType.items,
      selectedName = state.organisationType.value,
      isShowing = state.organisationType.isShowing,
      onMenuClick = {
        viewModel.onEvent(
          SignUpEvent.OrganisationTypesVisibilityChange(value = true)
        )
      },
      onItemClick = { index ->
        viewModel.onEvent(
          SignUpEvent.OrganisationTypeChange(value = state.organisationType.items[index])
        )
      },
      onDismissRequest = {
        viewModel.onEvent(
          SignUpEvent.OrganisationTypesVisibilityChange(value = false)
        )
      },
      hint = PresentationText.Resource(id = R.string.organization_type_hint)
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Resource(id = R.string.organization_information).asString()
    )

    FormDropdownField(
      fieldName = PresentationText.Resource(id = R.string.entity_type),
      items = state.entityType.items,
      selectedName = state.entityType.value,
      isShowing = state.entityType.isShowing,
      onMenuClick = {
        viewModel.onEvent(
          SignUpEvent.EntityTypesVisibilityChange(value = true)
        )
      },
      onItemClick = { index ->
        viewModel.onEvent(
          SignUpEvent.EntityTypeChange(value = state.entityType.items[index])
        )
      },
      onDismissRequest = {
        viewModel.onEvent(
          SignUpEvent.EntityTypesVisibilityChange(value = false)
        )
      },
      hint = PresentationText.Resource(id = R.string.entity_type_hint)
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.entity_name),
      fieldData = state.entityName,
      hint = PresentationText.Resource(id = R.string.entity_name_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.EntityNameChange(value = value))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(id = R.string.entity_address),
      fieldData = state.entityAddress,
      hint = PresentationText.Resource(id = R.string.entity_address_hint),
      onValueChange = { value ->
        viewModel.onEvent(event = SignUpEvent.EntityAddressChange(value = value))
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
  name = "Sign Up Tab Page Wholesale",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun SignUpTabPageWholesalePreview() {
  SignUpTabPageWholesale()
}