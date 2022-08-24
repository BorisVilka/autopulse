package ru.autopulse05.android.shared.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.core.presentation.ui.theme.BrandGray
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun SecondaryButton(
  text: PresentationText,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
) {
  Button(
    modifier = modifier,
    border = BorderStroke(width = 1.dp, color = Color.BrandGray),
    colors = colors,
    onClick = { onClick() },
  ) {
    Text(
      text = text.asString(),
      style = MaterialTheme.typography.button,
      color = MaterialTheme.colors.onSecondary
    )
  }
}

@Preview(
  name = "Secondary Button",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun SecondaryButtonPreview() {
  SecondaryButton(
    text = PresentationText.Dynamic("Click me"),
    onClick = {}
  )
}