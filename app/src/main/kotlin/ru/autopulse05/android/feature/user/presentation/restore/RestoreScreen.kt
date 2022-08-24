package ru.autopulse05.android.feature.user.presentation.restore

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
import ru.autopulse05.android.feature.user.presentation.restore.util.RestoreEvent
import ru.autopulse05.android.feature.user.presentation.restore.util.RestoreUiEvent
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.InputField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun RestoreScreen(
  navController: NavController,
  viewModel: RestoreViewModel = hiltViewModel()
) {

  val formState = viewModel.formState
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is RestoreUiEvent.Success -> navController.navigate(
          UserScreens.NewPassword.withArgs(formState.emailOrMobile)
        )
      }
    }
  }

  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier
        .padding(SpaceNormal)
        .background(
          color = MaterialTheme.colors.surface,
          shape = MaterialTheme.shapes.medium
        )
        .padding(SpaceNormal)
    ) {
      Text(
        text = PresentationText.Resource(R.string.password_restore).asString(),
        style = MaterialTheme.typography.h1
      )

      Spacer(modifier = Modifier.height(SpaceNormal))

      InputField(
        value = formState.emailOrMobile.value,
        onValueChange = { value ->
          viewModel.onEvent(RestoreEvent.EmailOrMobileChanged(value = value))
        },
        hint = PresentationText.Resource(R.string.email_or_phone).asString()
      )

      if (formState.hasError) {
        Spacer(modifier = Modifier.height(SpaceNormal))

        Text(
          text = PresentationText.Resource(R.string.email_or_number_not_found).asString(),
          color = MaterialTheme.colors.error
        )
      }

      Spacer(modifier = Modifier.height(SpaceNormal))

      BigButton(
        onClick = {
          viewModel.onEvent(RestoreEvent.Submit)
        },
        text = PresentationText.Resource(R.string.restore)
      )
    }
  }
}

@Preview(
  name = "Restore Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RestoreScreenPreview() {
  val navController = rememberNavController()

  RestoreScreen(navController = navController)
}