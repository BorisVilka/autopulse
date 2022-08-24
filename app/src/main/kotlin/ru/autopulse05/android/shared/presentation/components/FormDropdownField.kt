package ru.autopulse05.android.shared.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun FormDropdownField(
  items: List<String>,
  selectedName: String?,
  isShowing: Boolean,
  hint: PresentationText,
  modifier: Modifier = Modifier,
  fieldName: PresentationText? = null,
  isDisabled: Boolean = false,
  onMenuClick: () -> Unit,
  onItemClick: (Int) -> Unit,
  onDismissRequest: () -> Unit,
  maxHeight: Dp? = null,
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

  Box(
    modifier = modifier
  ) {
    DropdownMenuItem(
      onClick = { onMenuClick() },
      enabled = !isDisabled,
      modifier = Modifier
        .background(MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity))
    ) {
      Text(
        text = selectedName ?: hint.asString(),
        style = MaterialTheme.typography.subtitle1
      )
    }

    DropdownMenu(
      expanded = isShowing,
      onDismissRequest = { onDismissRequest() },
      modifier = Modifier
        .then(if (maxHeight != null) Modifier.height(maxHeight) else Modifier)
        .fillMaxWidth()
    ) {
      items.forEachIndexed { index, item ->
        DropdownMenuItem(
          onClick = { onItemClick(index) }
        ) {
          Text(
            text = item,
            style = MaterialTheme.typography.subtitle1
          )
        }
      }
    }
  }
}