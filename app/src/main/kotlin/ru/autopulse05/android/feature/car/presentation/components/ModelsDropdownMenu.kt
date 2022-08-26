package ru.autopulse05.android.feature.car.presentation.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.shared.presentation.components.FormDropdownField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ModelsDropdownMenu(
  items: List<CarModel>,
  selected: CarModel?,
  isShowing: Boolean,
  isDisabled: Boolean = false,
  onMenuClick: () -> Unit,
  onItemClick: (CarModel) -> Unit,
  onDismissRequest: () -> Unit,
  maxHeight: Dp? = 200.dp
) = FormDropdownField(
  fieldName = PresentationText.Resource(R.string.model),
  items = items.map { it.name.orEmpty() },
  selectedName = selected?.name,
  isShowing = isShowing,
  onMenuClick = onMenuClick,
  isDisabled = isDisabled,
  onItemClick = { index ->
    onItemClick(items[index])
  },
  onDismissRequest = onDismissRequest,
  maxHeight = maxHeight,
  hint = PresentationText.Resource(id = R.string.model)
)


@Preview(
  name = "Models Dropdown Menu",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ModelsDropdownMenuPreview() {
  val items = listOf(
    CarModel(
      id = 1,
      name = "first",
      yearFrom = "year from",
      yearTo = "year to"
    ),
    CarModel(
      id = 1,
      name = "second",
      yearFrom = "year from",
      yearTo = "year to"
    ),
  )

  val selected = remember { mutableStateOf(items[0]) }
  val isShowing = remember { mutableStateOf(true) }

  ModelsDropdownMenu(
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