package ru.autopulse05.android.shared.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.FormTextFieldData
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun FormTextField(
  fieldData: FormTextFieldData,
  modifier: Modifier = Modifier,
  fieldName: PresentationText? = null,
  keyboardType: KeyboardType = KeyboardType.Text,
  hint: PresentationText? = null,
  onValueChange: (String) -> Unit = {},
  visualTransformation: VisualTransformation = VisualTransformation.None
) {

  if (fieldName != null) {
    Text(
      text = fieldName.asString(),
      modifier = Modifier
        .padding(
          top = SpaceNormal,
          bottom = SpaceSmall
        ),
      style = MaterialTheme.typography.subtitle1
    )
  }

  TextField(
    modifier = modifier.fillMaxWidth(),
    value = fieldData.value,
    isError = fieldData.hasError,
    keyboardOptions = KeyboardOptions(
      keyboardType = keyboardType
    ),
    enabled = !fieldData.isDisabled,
    placeholder = {
      if (hint != null) {
        Text(text = hint.asString())
      }
    },
    visualTransformation = visualTransformation,
    onValueChange = onValueChange,
    colors = TextFieldDefaults.textFieldColors(
      backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity)
    )
  )

  if (fieldData.hasError) {
    Text(
      text = fieldData.error!!,
      color = MaterialTheme.colors.error
    )
  }
}