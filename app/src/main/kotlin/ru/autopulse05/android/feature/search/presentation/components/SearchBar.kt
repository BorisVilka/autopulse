package ru.autopulse05.android.feature.search.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun SearchBar(
  value: String,
  onValueChange: (String) -> Unit,
  hint: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.colors.secondary,
  enabled: Boolean = true,
) {
  Box(modifier = Modifier.background(color)) {
    BasicTextField(
      value = value,
      onValueChange = onValueChange,
      singleLine = true,
      enabled = enabled,
      modifier = modifier.fillMaxWidth(),
      decorationBox = { innerTextField ->
        Row(
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
          ) {
            if (value.isEmpty()) {
              Text(
                text = hint,
                color = MaterialTheme.colors.onSecondary
              )
            }
            innerTextField()
          }
          Icon(
            painter = painterResource(id = R.drawable.ic_magnifier),
            contentDescription = null,
            modifier = Modifier.padding(start = SpaceNormal),
            tint = MaterialTheme.colors.onSecondary
          )
        }
      },
      textStyle = TextStyle(color = MaterialTheme.colors.onSecondary)
    )
  }
}

@Preview(
  name = "VehicleSearch Bar",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SearchBarPreview() {
  SearchBar(
    value = "",
    onValueChange = { },
    hint = PresentationText.Resource(R.string.detail_number).asString(),
  )
}