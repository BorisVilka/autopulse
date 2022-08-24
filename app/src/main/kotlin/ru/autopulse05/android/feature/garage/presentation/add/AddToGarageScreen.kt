package ru.autopulse05.android.feature.garage.presentation.add

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.car.presentation.components.MarksDropdownMenu
import ru.autopulse05.android.feature.car.presentation.components.ModelsDropdownMenu
import ru.autopulse05.android.feature.car.presentation.components.ModificationsDropdownMenu
import ru.autopulse05.android.feature.car.presentation.components.YearsDropdownMenu
import ru.autopulse05.android.feature.garage.presentation.add.util.AddToGarageEvent
import ru.autopulse05.android.feature.garage.presentation.add.util.AddToGarageMode
import ru.autopulse05.android.feature.garage.presentation.add.util.AddToGarageUiEvent
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileTabs
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.FormTextField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun AddToGarageScreen(
  navController: NavController,
  onSuccessNavigate: String?,
  viewModel: AddToGarageViewModel = hiltViewModel()
) {
  val formState = viewModel.formState
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when(event) {
        AddToGarageUiEvent.NotLoggedIn -> {
          navController.popBackStack()
          navController.navigate(UserScreens.NotSignedIn.route)
        }
        AddToGarageUiEvent.Success -> navController.popBackStack()
      }
    }
  }

  Column(
    modifier = Modifier
      .background(color = MaterialTheme.colors.background)
      .padding(SpaceNormal)
      .verticalScroll(rememberScrollState())
  ) {
    FormTextField(
      fieldName = PresentationText.Resource(R.string.title),
      fieldData = formState.name,
      hint = PresentationText.Resource(R.string.title),
      onValueChange = { value ->
        viewModel.onEvent(AddToGarageEvent.TitleChanged(value = value))
      }
    )

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .padding(top = SpaceNormal)
        .fillMaxWidth()
    ) {

      Row(
        verticalAlignment = Alignment.CenterVertically,
      ) {
        RadioButton(
          selected = formState.mode == AddToGarageMode.VIN,
          onClick = {
            viewModel.onEvent(AddToGarageEvent.ModeChanged(value = AddToGarageMode.VIN))
          }
        )

        Spacer(modifier = Modifier.width(SpaceSmall))

        Text(text = PresentationText.Resource(R.string.vin_code).asString())
      }

      Spacer(modifier = Modifier.width(SpaceNormal))

      Row(
        verticalAlignment = Alignment.CenterVertically,
      ) {
        RadioButton(
          selected = formState.mode == AddToGarageMode.BODY,
          onClick = {
            viewModel.onEvent(AddToGarageEvent.ModeChanged(value = AddToGarageMode.BODY))
          }
        )

        Spacer(modifier = Modifier.width(SpaceSmall))

        Text(text = PresentationText.Resource(R.string.cascade_number).asString())
      }
    }

    if (formState.mode == AddToGarageMode.VIN) {
      FormTextField(
        fieldName = PresentationText.Resource(R.string.vin_code),
        fieldData = formState.vinCode,
        hint = PresentationText.Resource(R.string.vin_code),
        onValueChange = { value ->
          viewModel.onEvent(AddToGarageEvent.VinCodeChanged(value = value))
        }
      )
    } else {
      FormTextField(
        fieldName = PresentationText.Resource(R.string.cascade_number),
        fieldData = formState.frame,
        hint = PresentationText.Resource(R.string.cascade_number),
        onValueChange = { value ->
          viewModel.onEvent(AddToGarageEvent.CascadeNumberChanged(value = value))
        }
      )
    }

    YearsDropdownMenu(
      items = formState.year.items,
      selected = formState.year.value,
      isShowing = formState.year.isShowing,
      isDisabled = formState.year.isDisabled,
      onMenuClick = {
        viewModel.onEvent(AddToGarageEvent.YearsVisibilityChanged(value = true))
      },
      onItemClick = { value ->
        viewModel.onEvent(AddToGarageEvent.YearChanged(value = value))
      },
      onDismissRequest = {
        viewModel.onEvent(AddToGarageEvent.YearsVisibilityChanged(value = false))
      }
    )

    MarksDropdownMenu(
      items = formState.mark.items,
      selected = formState.mark.value,
      isShowing = formState.mark.isShowing,
      isDisabled = formState.mark.isDisabled,
      onMenuClick = {
        viewModel.onEvent(AddToGarageEvent.MarksVisibilityChanged(value = true))
      },
      onItemClick = { value ->
        viewModel.onEvent(AddToGarageEvent.MarkChanged(value = value))
      },
      onDismissRequest = {
        viewModel.onEvent(AddToGarageEvent.MarksVisibilityChanged(value = false))
      }
    )

    ModelsDropdownMenu(
      items = formState.model.items,
      selected = formState.model.value,
      isShowing = formState.model.isShowing,
      isDisabled = formState.model.isDisabled,
      onMenuClick = {
        viewModel.onEvent(AddToGarageEvent.ModelsVisibilityChanged(value = true))
      },
      onItemClick = { value ->
        viewModel.onEvent(AddToGarageEvent.ModelChanged(value = value))
      },
      onDismissRequest = {
        viewModel.onEvent(AddToGarageEvent.ModelsVisibilityChanged(value = false))
      }
    )

    ModificationsDropdownMenu(
      items = formState.modification.items,
      selected = formState.modification.value,
      isShowing = formState.modification.isShowing,
      isDisabled = formState.modification.isDisabled,
      onMenuClick = {
        viewModel.onEvent(AddToGarageEvent.ModificationsVisibilityChanged(value = true))
      },
      onItemClick = { value ->
        viewModel.onEvent(AddToGarageEvent.ModificationChanged(value = value))
      },
      onDismissRequest = {
        viewModel.onEvent(AddToGarageEvent.ModificationsVisibilityChanged(value = false))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(R.string.country_number),
      fieldData = formState.regPlate,
      hint = PresentationText.Resource(R.string.country_number),
      onValueChange = { value ->
        viewModel.onEvent(AddToGarageEvent.CountryNumberChanged(value = value))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(R.string.mileage),
      fieldData = formState.mileage,
      hint = PresentationText.Resource(R.string.mileage),
      onValueChange = { value ->
        viewModel.onEvent(AddToGarageEvent.MileageChanged(value = value))
      }
    )

    FormTextField(
      fieldName = PresentationText.Resource(R.string.description),
      fieldData = formState.description,
      hint = PresentationText.Resource(R.string.description),
      onValueChange = { value ->
        viewModel.onEvent(AddToGarageEvent.DescriptionChanged(value = value))
      }
    )

    Spacer(modifier = Modifier.height(SpaceLarge))

    BigButton(
      onClick = {
                viewModel.onEvent(AddToGarageEvent.Submit)
      },
      text = PresentationText.Dynamic("Добавить"))
  }
}

@Preview(
  name = "Add To Garage Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AddToGarageScreenPreview() {
  val navController = rememberNavController()

  AddToGarageScreen(
    navController = navController,
    onSuccessNavigate = UserScreens.Profile.withArgs(ProfileTabs.Garage.index)
  )
}
