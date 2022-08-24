package ru.autopulse05.android.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.autopulse05.android.core.presentation.util.Navigation
import ru.autopulse05.android.feature.user.presentation.profile.util.rememberProfileTabState
import ru.autopulse05.android.shared.presentation.popup.rememberPopupState

@AndroidEntryPoint
@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val navController = rememberNavController()
      val scaffoldState = rememberScaffoldState()
      val popupState = rememberPopupState()
      val profileTabState = rememberProfileTabState()

      AutopulseApp(
        navController = navController,
        scaffoldState = scaffoldState,
        popupState = popupState,
        profileTabState = profileTabState
      ) {
        Navigation(
          navController = navController,
          scaffoldState = scaffoldState,
          popupState = popupState,
          profileTabState = profileTabState
        )
      }
    }
  }
}