package ru.autopulse05.android.shared.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun LoadingScreen(
  isLoading: Boolean,
  content: @Composable () -> Unit
) = if (isLoading) {
  Box(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier.align(Alignment.Center),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      CircularProgressIndicator()
      Spacer(modifier = Modifier.height(SpaceNormal))
      Text(
        text = "Подождите",
        fontSize = 26.sp
      )
      Spacer(modifier = Modifier.height(SpaceSmall))
      Text(
        text = "Идёт загрузка",
        fontSize = 18.sp
      )

    }
  }
} else content()


@Preview(
  name = "Loading Screen",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun LoadingScreenPreview() {
  LoadingScreen(
    isLoading = true,
    content = {}
  )
}