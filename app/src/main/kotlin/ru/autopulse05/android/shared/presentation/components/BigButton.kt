package ru.autopulse05.android.shared.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun BigButton(
  onClick: () -> Unit,
  text: PresentationText,
  modifier: Modifier = Modifier,
  isDisabled: Boolean = false,
  colors: ButtonColors = ButtonDefaults.buttonColors(),
  textStyle: TextStyle = MaterialTheme.typography.button
) {
  Button(
    onClick = { onClick() },
    enabled = !isDisabled,
    contentPadding = PaddingValues(vertical = SpaceNormal),
    modifier = modifier.fillMaxWidth(),
    colors = colors
  ) {
    Text(
      text = text.asString(),
      style = textStyle
    )
  }
}

@Preview
@Composable
fun BigButtonPreview() {
  BigButton(
    text = PresentationText.Dynamic("Text"),
    onClick = {}
  )
}