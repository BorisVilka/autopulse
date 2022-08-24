package ru.autopulse05.android.shared.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal

@Composable
fun InputField(
  value: String,
  onValueChange: (String) -> Unit,
  hint: String
) {
  Box {
    BasicTextField(
      value = value,
      onValueChange = onValueChange,
      singleLine = true,
      textStyle = TextStyle(color = MaterialTheme.colors.onSecondary),
      modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity))
        .padding(SpaceNormal)
    )

    if (value.isEmpty()) {
      Text(
        text = hint,
        modifier = Modifier.padding(SpaceNormal),
        color = MaterialTheme.colors.onSecondary
      )
    }
  }
}