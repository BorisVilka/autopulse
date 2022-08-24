package ru.autopulse05.android.shared.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

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