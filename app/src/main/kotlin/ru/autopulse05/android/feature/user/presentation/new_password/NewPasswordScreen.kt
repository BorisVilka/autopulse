package ru.autopulse05.android.feature.user.presentation.new_password

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
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
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.feature.user.presentation.new_password.util.NewPasswordEvent
import ru.autopulse05.android.feature.user.presentation.new_password.util.NewPasswordUiEvent
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.InputField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun NewPasswordScreen(
  navController: NavController,
  phone: String,
  viewModel: NewPasswordViewModel = hiltViewModel()
) {

  val formState = viewModel.formState
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.onEvent(NewPasswordEvent.PhoneChanged(value = phone))
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is NewPasswordUiEvent.Success -> navController.navigate(StoreScreens.Main.route)
      }
    }
  }

  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(SpaceNormal)
        .background(
          color = MaterialTheme.colors.surface,
          shape = MaterialTheme.shapes.medium
        )
        .padding(SpaceNormal)
    ) {

      InputField(
        value = formState.code.value,
        onValueChange = { value ->
          viewModel.onEvent(NewPasswordEvent.CodeChanged(value = value))
        },
        hint = PresentationText.Resource(R.string.code).asString()
      )

      Spacer(modifier = Modifier.height(SpaceNormal))

      InputField(
        value = formState.newPassword.value,
        onValueChange = { value ->
          viewModel.onEvent(NewPasswordEvent.NewPasswordChanged(value = value))
        },
        hint = PresentationText.Resource(R.string.new_password).asString()
      )

      Spacer(modifier = Modifier.height(SpaceNormal))

      BigButton(
        onClick = {
          viewModel.onEvent(NewPasswordEvent.Submit)
        },
        text = PresentationText.Resource(R.string.enter)
      )

      if (formState.hasError) {
        Spacer(modifier = Modifier.height(SpaceNormal))

        Text(
          text = PresentationText.Resource(R.string.wrong_code_or_password).asString(),
          color = MaterialTheme.colors.error
        )
      }
    }
  }
}

@Preview(
  name = "New Password Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun NewPasswordScreenPreview() {
  val navController = rememberNavController()

  NewPasswordScreen(
    navController = navController,
    phone = "+799912345678"
  )
}