package ru.autopulse05.android.feature.car.presentation.edit

import android.content.res.Configuration
import android.widget.Space
import android.widget.Toast
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
import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.car.presentation.components.MarksDropdownMenu
import ru.autopulse05.android.feature.car.presentation.components.ModelsDropdownMenu
import ru.autopulse05.android.feature.car.presentation.components.ModificationsDropdownMenu
import ru.autopulse05.android.feature.car.presentation.components.YearsDropdownMenu
import ru.autopulse05.android.feature.car.presentation.edit.util.CarEditEvent
import ru.autopulse05.android.feature.car.presentation.edit.util.CarEditMode
import ru.autopulse05.android.feature.car.presentation.edit.util.CarEditUiEvent
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.FormTextField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun CarEditScreen(
  navController: NavController,
  car: Car? = null,
  viewModel: CarEditViewModel = hiltViewModel()
) {
  val state = viewModel.state
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(CarEditEvent.InitialValuesChange(car = car))
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        CarEditUiEvent.NotLoggedIn -> {
          navController.popBackStack()
          navController.navigate(UserScreens.NotSignedIn.route)
        }
        is CarEditUiEvent.Success -> navController.popBackStack()
        is CarEditUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
      }
    }
  }

  LoadingScreen(isLoading = state.isLoading) {
    Column(
      modifier = Modifier
        .background(color = MaterialTheme.colors.background)
        .padding(SpaceNormal)
        .verticalScroll(rememberScrollState())
    ) {
      FormTextField(
        fieldName = PresentationText.Resource(R.string.title),
        fieldData = state.name,
        hint = PresentationText.Resource(R.string.title),
        onValueChange = { value ->
          viewModel.onEvent(CarEditEvent.TitleChange(value = value))
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
            selected = state.mode == CarEditMode.VIN,
            onClick = {
              viewModel.onEvent(CarEditEvent.ModeChange(value = CarEditMode.VIN))
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
            selected = state.mode == CarEditMode.BODY,
            onClick = {
              viewModel.onEvent(CarEditEvent.ModeChange(value = CarEditMode.BODY))
            }
          )

          Spacer(modifier = Modifier.width(SpaceSmall))

          Text(text = PresentationText.Resource(R.string.cascade_number).asString())
        }
      }

      if (state.mode == CarEditMode.VIN) {
        FormTextField(
          fieldName = PresentationText.Resource(R.string.vin_code),
          fieldData = state.vin,
          hint = PresentationText.Resource(R.string.vin_code),
          onValueChange = { value ->
            viewModel.onEvent(CarEditEvent.VinCodeChange(value = value))
          }
        )
      } else {
        FormTextField(
          fieldName = PresentationText.Resource(R.string.cascade_number),
          fieldData = state.frame,
          hint = PresentationText.Resource(R.string.cascade_number),
          onValueChange = { value ->
            viewModel.onEvent(CarEditEvent.CascadeNumberChange(value = value))
          }
        )
      }

      YearsDropdownMenu(
        items = state.year.items,
        selected = state.year.value,
        isShowing = state.year.isShowing,
        isDisabled = state.year.isDisabled,
        onMenuClick = {
          viewModel.onEvent(CarEditEvent.YearsVisibilityChange(value = true))
        },
        onItemClick = { value ->
          viewModel.onEvent(CarEditEvent.YearChange(value = value))
        },
        onDismissRequest = {
          viewModel.onEvent(CarEditEvent.YearsVisibilityChange(value = false))
        }
      )

      MarksDropdownMenu(
        items = state.mark.items,
        selected = state.mark.value,
        isShowing = state.mark.isShowing,
        isDisabled = state.mark.isDisabled,
        onMenuClick = {
          viewModel.onEvent(CarEditEvent.MarksVisibilityChange(value = true))
        },
        onItemClick = { value ->
          viewModel.onEvent(CarEditEvent.MarkChange(value = value))
        },
        onDismissRequest = {
          viewModel.onEvent(CarEditEvent.MarksVisibilityChange(value = false))
        }
      )

      ModelsDropdownMenu(
        items = state.model.items,
        selected = state.model.value,
        isShowing = state.model.isShowing,
        isDisabled = state.model.isDisabled,
        onMenuClick = {
          viewModel.onEvent(CarEditEvent.ModelsVisibilityChange(value = true))
        },
        onItemClick = { value ->
          viewModel.onEvent(CarEditEvent.ModelChange(value = value))
        },
        onDismissRequest = {
          viewModel.onEvent(CarEditEvent.ModelsVisibilityChange(value = false))
        }
      )

      ModificationsDropdownMenu(
        items = state.modification.items,
        selected = state.modification.value,
        isShowing = state.modification.isShowing,
        isDisabled = state.modification.isDisabled,
        onMenuClick = {
          viewModel.onEvent(CarEditEvent.ModificationsVisibilityChange(value = true))
        },
        onItemClick = { value ->
          viewModel.onEvent(CarEditEvent.ModificationChange(value = value))
        },
        onDismissRequest = {
          viewModel.onEvent(CarEditEvent.ModificationsVisibilityChange(value = false))
        }
      )

      FormTextField(
        fieldName = PresentationText.Resource(R.string.country_number),
        fieldData = state.regPlate,
        hint = PresentationText.Resource(R.string.country_number),
        onValueChange = { value ->
          viewModel.onEvent(CarEditEvent.CountryNumberChange(value = value))
        }
      )

      FormTextField(
        fieldName = PresentationText.Resource(R.string.mileage),
        fieldData = state.mileage,
        hint = PresentationText.Resource(R.string.mileage),
        onValueChange = { value ->
          viewModel.onEvent(CarEditEvent.MileageChange(value = value))
        }
      )

      FormTextField(
        fieldName = PresentationText.Resource(R.string.description),
        fieldData = state.description,
        hint = PresentationText.Resource(R.string.description),
        onValueChange = { value ->
          viewModel.onEvent(CarEditEvent.DescriptionChange(value = value))
        }
      )

      Spacer(modifier = Modifier.height(SpaceLarge))

      BigButton(
        onClick = { viewModel.onEvent(CarEditEvent.Submit) },
        text = PresentationText.Dynamic("Сохранить"))
    }
  }
}

@Preview(
  name = "Car Edit Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CarEditScreenPreview() {
  val navController = rememberNavController()

  CarEditScreen(
    navController = navController
  )
}
