package ru.autopulse05.android.feature.vin.presentation.guest

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraSmall
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.feature.vin.domain.model.CarInfo
import ru.autopulse05.android.feature.vin.presentation.guest.util.VinGuestEvent
import ru.autopulse05.android.feature.vin.presentation.guest.util.VinGuestUiEvent
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.FormTextField
import ru.autopulse05.android.shared.presentation.util.PresentationText


@Composable
fun VinGuestScreen(
  navController: NavController,
  carInfo: CarInfo?,
  parts: List<String>?,
  viewModel: VinGuestViewModel = hiltViewModel()
) {
  val state = viewModel.state
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is VinGuestUiEvent.GoToStore -> navController.navigate(StoreScreens.Main.route)
        is VinGuestUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
      }
    }
  }

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(
      VinGuestEvent.InitialValuesChange(
        carInfo = carInfo!!,
        parts = parts!!
      )
    )
  }

  LoadingScreen(isLoading = state.isLoading) {
    Column(
      modifier = Modifier
        .padding(SpaceNormal)
        .fillMaxSize(),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = PresentationText.Dynamic("Подбор запчастей по VIN коду").asString(),
          style = MaterialTheme.typography.h1
        )

        Spacer(modifier = Modifier.height(SpaceSmall))

        Text(
          text = PresentationText.Dynamic("Укажите контактную информацию")
            .asString(),
          style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(SpaceNormal))

        FormTextField(
          fieldName = PresentationText.Resource(id = R.string.name),
          fieldData = state.name,
          hint = PresentationText.Resource(id = R.string.name_hint),
          onValueChange = { value ->
            viewModel.onEvent(VinGuestEvent.NameChange(value = value))
          }
        )

        FormTextField(
          fieldName = PresentationText.Resource(id = R.string.phone),
          fieldData = state.phone,
          keyboardType = KeyboardType.Phone,
          hint = PresentationText.Resource(id = R.string.phone_hint),
          onValueChange = { value ->
            viewModel.onEvent(VinGuestEvent.PhoneChange(value = value))
          }
        )

        FormTextField(
          fieldName = PresentationText.Resource(id = R.string.email),
          fieldData = state.email,
          keyboardType = KeyboardType.Email,
          hint = PresentationText.Resource(id = R.string.email_hint),
          onValueChange = { value ->
            viewModel.onEvent(VinGuestEvent.EmailChange(value = value))
          }
        )

        Spacer(modifier = Modifier.height(SpaceNormal))

        Row(
          modifier = Modifier.fillMaxWidth().clickable {
            viewModel.onEvent(VinGuestEvent.TermsAgreementChange(value = !state.termsAgreement))
          }
        ) {
          Checkbox(
            checked = state.termsAgreement,
            onCheckedChange = null
          )

          Spacer(modifier = Modifier.width(SpaceSmall))

          Text(
            text = PresentationText.Resource(id = R.string.terms_agreement).asString()
          )
        }
      }

      BigButton(
        modifier = Modifier.padding(top = SpaceNormal),
        text = PresentationText.Dynamic("Оформить"),
        onClick = { viewModel.onEvent(event = VinGuestEvent.Submit) },
      )
    }
  }

  Column(
    modifier = Modifier.padding(SpaceLarge),
  ) {


    BigButton(
      modifier = Modifier.padding(top = SpaceNormal),
      text = PresentationText.Resource(R.string.continue_word),
      isDisabled = state.isLoading,
      onClick = {
        viewModel.onEvent(event = VinGuestEvent.Submit)
      },
    )
  }
}

@Preview(
  name = "Vin Guest Screen",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun VinGuestScreenPreview() {
  val navController = rememberNavController()

  VinGuestScreen(
    navController = navController,
    carInfo = CarInfo(
      brand = "brand",
      vin = "vin",
      frame = "frame",
    ),
    parts = listOf()
  )
}