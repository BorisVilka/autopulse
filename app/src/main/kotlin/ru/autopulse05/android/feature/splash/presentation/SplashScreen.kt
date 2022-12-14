package ru.autopulse05.android.feature.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SPLASH_SCREEN_DURATION
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.shared.presentation.util.PresentationText


@Composable
fun SplashScreen(
  navController: NavController
) {
  val context = LocalContext.current

  LaunchedEffect(key1 = context) {
    delay(SPLASH_SCREEN_DURATION)
    navController.popBackStack()
    navController.navigate(StoreScreens.Main.route)
  }

  Box(
    modifier = Modifier.fillMaxSize().padding(SpaceLarge),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(id = R.drawable.spalsh),
      contentDescription = PresentationText.Resource(id = R.string.app_logo).asString(),
      contentScale = ContentScale.Fit,
    )
  }

}

@Preview
@Composable
fun SplashScreenPreview() {
  val navController = rememberNavController()

    ru.autopulse05.android.core.presentation.splash.SplashScreen(
        navController = navController
    )
}