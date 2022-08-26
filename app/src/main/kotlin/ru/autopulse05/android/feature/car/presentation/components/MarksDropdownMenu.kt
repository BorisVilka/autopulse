package ru.autopulse05.android.feature.car.presentation.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.shared.presentation.components.FormDropdownField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun MarksDropdownMenu(
  items: List<CarMark>,
  selected: CarMark?,
  isShowing: Boolean,
  isDisabled: Boolean = false,
  onMenuClick: () -> Unit,
  onItemClick: (CarMark) -> Unit,
  onDismissRequest: () -> Unit,
  maxHeight: Dp? = null
) = FormDropdownField(
  items = items.map { it.name.orEmpty() },
  fieldName = PresentationText.Resource(id = R.string.car_mark),
  selectedName = selected?.name,
  isShowing = isShowing,
  isDisabled = isDisabled,
  onMenuClick = onMenuClick,
  onItemClick = { index ->
    onItemClick(items[index])
  },
  onDismissRequest = onDismissRequest,
  maxHeight = maxHeight,
  hint = PresentationText.Resource(id = R.string.car_mark)
)


@Preview(
  name = "Marks Dropdown Menu",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MarksDropdownMenuPreview() {
  val items = listOf(
    CarMark(
      id = 1,
      name = "first"
    ),
    CarMark(
      id = 1,
      name = "second"
    ),
  )

  val selected = remember { mutableStateOf(items[0]) }
  val isShowing = remember { mutableStateOf(true) }

  MarksDropdownMenu(
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