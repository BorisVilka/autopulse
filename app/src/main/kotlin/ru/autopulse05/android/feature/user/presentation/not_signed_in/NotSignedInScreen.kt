package ru.autopulse05.android.feature.user.presentation.not_signed_in


import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.user.presentation.not_signed_in.util.NotSignedInEvent
import ru.autopulse05.android.feature.user.presentation.not_signed_in.util.NotSignedInUiEvent
import ru.autopulse05.android.feature.user.presentation.util.UserScreens
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun NotSignedInScreen(
  navController: NavController,
  viewModel: NotSignedInViewModel = hiltViewModel()
) {
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is NotSignedInUiEvent.SignIn -> navController.navigate(
          UserScreens.SignIn.withArgs("?password=")
        )
        is NotSignedInUiEvent.SignUp -> navController.navigate(UserScreens.SignUp.route)
      }
    }
  }

  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = PresentationText.Resource(id = R.string.only_for_signed).asString(),
      style = MaterialTheme.typography.body1,
      textAlign = TextAlign.Center
    )
  }

  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.BottomCenter
  ) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(SpaceNormal)
    ) {
      BigButton(
        onClick = {
          viewModel.onEvent(event = NotSignedInEvent.SignIn)
        },
        text = PresentationText.Resource(R.string.sign_in_now)
      )

      Spacer(modifier = Modifier.height(SpaceNormal))

      BigButton(
        onClick = {
          viewModel.onEvent(event = NotSignedInEvent.SignUp)
        },
        text = PresentationText.Resource(R.string.sign_up_now),
        colors = ButtonDefaults.buttonColors(
          backgroundColor = MaterialTheme.colors.secondary
        ),
        textStyle = TextStyle(
          fontWeight = FontWeight.SemiBold,
          fontSize = 14.sp,
          color = MaterialTheme.colors.onSecondary
        )
      )

      Spacer(modifier = Modifier.height(SpaceNormal))
    }
  }
}

@Preview(
  name = "Not Signed In Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileScreenPreview() {
  val navController = rememberNavController()

  NotSignedInScreen(
    navController = navController
  )
}