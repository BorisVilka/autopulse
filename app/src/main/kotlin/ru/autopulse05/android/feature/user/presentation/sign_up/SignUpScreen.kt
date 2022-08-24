package ru.autopulse05.android.feature.user.presentation.sign_up

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.feature.user.presentation.sign_up.components.SignUpTabLayout
import ru.autopulse05.android.feature.user.presentation.sign_up.components.SignUpTabPage
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpEvent
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpTabs
import ru.autopulse05.android.feature.user.presentation.sign_up.util.SignUpUiEvent
import ru.autopulse05.android.feature.user.presentation.sign_up.util.rememberSignUpTabState
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.CheckboxWithText
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun SignUpScreen(
  navController: NavController,
  viewModel: SignUpViewModel = hiltViewModel()
) {
  val state = viewModel.state
  val signUpTabState = rememberSignUpTabState(initialValue = SignUpTabs.Retail)
  val scrollState = rememberScrollState()
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is SignUpUiEvent.Success -> {
          navController.popBackStack()
          navController.navigate(StoreScreens.Main.route)
        }
        is SignUpUiEvent.TabChange -> signUpTabState.change(event.value)
        is SignUpUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
      }
    }
  }

  LoadingScreen(isLoading = state.isLoading) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
        .padding(SpaceNormal)
    ) {
      SignUpTabLayout(signUpTabState = signUpTabState)

      SignUpTabPage(signUpTabState = signUpTabState)

      Spacer(modifier = Modifier.height(SpaceNormal))

      CheckboxWithText(
        checked = state.termsAgreement,
        onClick = {
          viewModel.onEvent(SignUpEvent.TermsAgreementChange(value = !state.termsAgreement))
        },
        text = PresentationText.Resource(id = R.string.terms_agreement)
      )

      BigButton(
        modifier = Modifier.padding(top = SpaceNormal),
        isDisabled = !state.termsAgreement,
        text = PresentationText.Resource(R.string.sign_up_now),
        onClick = {
          viewModel.onEvent(event = SignUpEvent.Submit(signUpTabState.current!!))
        },
      )
    }
  }
}

@Preview(
  name = "Sign Up Screen",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun SignUpScreenPreview() {
  val navController = rememberNavController()

  SignUpScreen(
    navController = navController
  )
}