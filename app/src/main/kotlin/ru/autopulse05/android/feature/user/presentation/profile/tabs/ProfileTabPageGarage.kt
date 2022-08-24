package ru.autopulse05.android.feature.user.presentation.profile.tabs

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.car.presentation.edit.components.GarageCar
import ru.autopulse05.android.feature.user.presentation.profile.ProfileViewModel
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileEvent
import ru.autopulse05.android.feature.user.presentation.profile.util.ProfileUiEvent
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ProfileTabPageGarage(
  viewModel: ProfileViewModel = hiltViewModel()
) {
  val state = viewModel.state


  if (state.cars.isEmpty()) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = PresentationText.Dynamic("Автомобилей нет").asString()
      )
    }
  }

  Column {
    state.cars.forEach { car ->
      GarageCar(car = car,
      onClick = {
          viewModel.editCar(it)
        }
      )
    }

    Spacer(modifier = Modifier.height(SpaceNormal))

    BigButton(
      onClick = {
        viewModel.onEvent(ProfileEvent.AddCar)
      },
      text = PresentationText.Resource(R.string.add_car)
    )
  }
}

@Preview(
  name = "Profile Tab Page Garage",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileTabPageGaragePreview() {
  ProfileTabPageGarage()
}