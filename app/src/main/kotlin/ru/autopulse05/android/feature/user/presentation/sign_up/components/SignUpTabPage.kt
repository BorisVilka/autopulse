package ru.autopulse05.android.feature.user.presentation.sign_up.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpTabState
import ru.autopulse05.android.feature.user.presentation.sign_up.util.rememberSignUpTabState

@Composable
fun SignUpTabPage(signUpTabState: SignUpTabState) {
  if (signUpTabState.current != null) {
    signUpTabState.current!!.draw()
  }
}

@Preview(
  name = "Sign Up Tab Page",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun SignUpTabPagePreview() {
  val signUpTabState = rememberSignUpTabState()

  SignUpTabPage(
    signUpTabState = signUpTabState
  )
}