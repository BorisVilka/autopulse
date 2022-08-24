package ru.autopulse05.android.feature.user.presentation.sign_up.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.user.presentation.sign_up.SignUpViewModel
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpEvent
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpTabState
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpTabs
import ru.autopulse05.android.feature.user.presentation.sign_up.util.rememberSignUpTabState
import ru.autopulse05.android.shared.presentation.components.SecondaryButton

@Composable
fun SignUpTabLayout(
  signUpTabState: SignUpTabState,
  viewModel: SignUpViewModel = hiltViewModel()
) {
  Row(modifier = Modifier.fillMaxWidth()) {

    SecondaryButton(
      text = SignUpTabs.Retail.title,
      modifier = Modifier.weight(0.5F),
      colors = ButtonDefaults.buttonColors(
        backgroundColor = if (signUpTabState.current == SignUpTabs.Retail) {
          MaterialTheme.colors.background
        } else MaterialTheme.colors.surface
      ),
      onClick = {
        viewModel.onEvent(SignUpEvent.TabChange(SignUpTabs.Retail))
      }
    )

    Spacer(modifier = Modifier.width(SpaceSmall))

    SecondaryButton(
      text = SignUpTabs.Wholesale.title,
      modifier = Modifier.weight(0.5F),
      colors = ButtonDefaults.buttonColors(
        backgroundColor = if (signUpTabState.current == SignUpTabs.Wholesale) {
          MaterialTheme.colors.background
        } else MaterialTheme.colors.surface
      ),
      onClick = {
        viewModel.onEvent(SignUpEvent.TabChange(SignUpTabs.Wholesale))
      }
    )
  }
}

@Preview(
  name = "Sign Up Tab Layout",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun SignUpTabLayoutPreview() {
  val signUpTabState = rememberSignUpTabState()

  SignUpTabLayout(
    signUpTabState = signUpTabState
  )
}