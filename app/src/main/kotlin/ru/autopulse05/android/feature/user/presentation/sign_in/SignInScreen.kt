package ru.autopulse05.android.feature.user.presentation.sign_in

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraSmall
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.feature.user.presentation.sign_in.util.SignInEvent
import ru.autopulse05.android.feature.user.presentation.sign_in.util.SignInUiEvent
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.InputField
import ru.autopulse05.android.shared.presentation.util.PresentationText


@Composable
fun SignInScreen(
  navController: NavController,
  password: String? = null,
  viewModel: SignInViewModel = hiltViewModel()
) {
  val formState = viewModel.formState
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    if (password != null) viewModel.onEvent(SignInEvent.PasswordChange(value = password))
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is SignInUiEvent.Success -> {
          navController.popBackStack()
          navController.navigate(StoreScreens.Main.route)
        }
        is SignInUiEvent.SignUp -> navController.navigate(UserScreens.SignUp.route)
        is SignInUiEvent.ForgotPassword -> navController.navigate(UserScreens.RestorePassword.route)
      }
    }
  }

  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
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
        value = formState.login.value,
        onValueChange = { value ->
          viewModel.onEvent(event = SignInEvent.LoginChange(value = value))
        },
        hint = PresentationText.Resource(R.string.login).asString()
      )

      Spacer(modifier = Modifier.height(SpaceNormal))

      InputField(
        value = formState.password.value,
        onValueChange = { value ->
          viewModel.onEvent(event = SignInEvent.PasswordChange(value = value))
        },
        hint = PresentationText.Resource(R.string.password_hint).asString()
      )

      Spacer(modifier = Modifier.height(SpaceNormal))

      BigButton(
        onClick = {
          viewModel.onEvent(event = SignInEvent.Submit)
        },
        text = PresentationText.Resource(R.string.enter)
      )

      if (formState.hasError) {
        Spacer(modifier = Modifier.height(SpaceNormal))

        Text(
          text = PresentationText.Resource(R.string.error).asString(),
          color = MaterialTheme.colors.error
        )
      }

      Spacer(modifier = Modifier.height(SpaceNormal))

      Row(
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = PresentationText.Resource(id = R.string.forgot_password).asString(),
          modifier = Modifier
            .clip(shape = MaterialTheme.shapes.small)
            .clickable {
              viewModel.onEvent(SignInEvent.ForgotPassword)
            }
            .padding(SpaceExtraSmall)
        )

        Text(
          text = PresentationText.Resource(id = R.string.sign_in).asString(),
          modifier = Modifier
            .clip(shape = MaterialTheme.shapes.small)
            .clickable {
              viewModel.onEvent(SignInEvent.NotSignedUp)
            }
            .padding(SpaceExtraSmall)
        )
      }
    }
  }
}

@Preview(
  name = "Sign In Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SignInScreenPreview() {
  val navController = rememberNavController()

  SignInScreen(navController = navController)
}