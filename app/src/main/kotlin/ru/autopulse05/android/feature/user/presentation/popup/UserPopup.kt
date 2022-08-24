package ru.autopulse05.android.feature.user.presentation.popup

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.user.presentation.popup.components.UserPopupTitle
import ru.autopulse05.android.feature.user.presentation.popup.util.UserPopupEvent
import ru.autopulse05.android.feature.user.presentation.popup.util.UserPopupLinks
import ru.autopulse05.android.feature.user.presentation.popup.util.UserPopupUiEvent
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.SecondaryButton
import ru.autopulse05.android.shared.presentation.popup.PopupState
import ru.autopulse05.android.shared.presentation.popup.rememberPopupState
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun UserPopup(
  navController: NavController,
  popupState: PopupState,
  viewModel: UserPopupViewModel = hiltViewModel()
) {
  val state = viewModel.state
  val context = LocalContext.current
  val links = listOf(
    UserPopupLinks.Orders,
    UserPopupLinks.Garage,
    UserPopupLinks.Profile
  )

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is UserPopupUiEvent.Close -> popupState.close()
        is UserPopupUiEvent.ItemClick -> navController.navigate(event.value.route)
        is UserPopupUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
      }
    }
  }

  if (!state.isLoading && popupState.isOpen && !popupState.isClosed && state.user!=null) {
    val user = state.user

    Dialog(onDismissRequest = { viewModel.onEvent(UserPopupEvent.Close) }) {
      Surface(color = MaterialTheme.colors.secondary, shape = MaterialTheme.shapes.small) {
        Column(modifier = Modifier.padding(SpaceNormal)) {
          UserPopupTitle(id = user.id, name = user.name)

          Spacer(modifier = Modifier.height(SpaceSmall))

          Column {
            for (link in links) {
              SecondaryButton(
                text = link.title,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                  viewModel.onEvent(UserPopupEvent.ItemClick(value = link))
                }
              )
            }
          }

          Spacer(modifier = Modifier.height(SpaceLarge))

          BigButton(
            text = PresentationText.Resource(id = R.string.log_out),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
              viewModel.onEvent(UserPopupEvent.SignOut)
            }
          )
        }
      }
    }
  }
}

@Preview(
  name = "User Popup",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun UserPopupPreview() {
  val navController = rememberNavController()
  val popupState = rememberPopupState()

  UserPopup(
    navController = navController,
    popupState = popupState
  )
}