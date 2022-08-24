package ru.autopulse05.android.feature.car.presentation.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import ru.autopulse05.android.R
import ru.autopulse05.android.shared.presentation.components.FormDropdownField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun YearsDropdownMenu(
  items: List<Int>,
  selected: Int?,
  isDisabled: Boolean = false,
  isShowing: Boolean,
  onMenuClick: () -> Unit,
  onItemClick: (Int) -> Unit,
  onDismissRequest: () -> Unit,
  maxHeight: Dp? = null
) = FormDropdownField(
  fieldName = PresentationText.Resource(R.string.year),
  items = items.map { it.toString() },
  selectedName = selected?.toString(),
  isShowing = isShowing,
  isDisabled = isDisabled,
  onMenuClick = onMenuClick,
  onItemClick = { index ->
    onItemClick(items[index])
  },
  onDismissRequest = onDismissRequest,
  maxHeight = maxHeight,
  hint = PresentationText.Resource(id = R.string.year)
)

@Preview(
  name = "Years Dropdown Menu",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun YearsDropdownMenuPreview() {
  val items = listOf(
    2002,
    2022
  )

  val selected = remember { mutableStateOf(items[0]) }
  val isShowing = remember { mutableStateOf(true) }

  YearsDropdownMenu(
    items = items,
    selected = selected.value,
    isShowing = isShowing.value,
    onMenuClick = {
      isShowing.value = true
    },
    onItemClick = { value ->
      selected.value = value
      isShowing.value = false
    },
    onDismissRequest = {
      isShowing.value = false
    }
  )
}