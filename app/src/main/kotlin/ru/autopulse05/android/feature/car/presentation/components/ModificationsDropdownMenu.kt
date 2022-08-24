package ru.autopulse05.android.feature.car.presentation.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.shared.presentation.components.FormDropdownField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ModificationsDropdownMenu(
  items: List<CarModification>,
  selected: CarModification?,
  isShowing: Boolean,
  isDisabled: Boolean = false,
  onMenuClick: () -> Unit,
  onItemClick: (CarModification) -> Unit,
  onDismissRequest: () -> Unit,
  maxHeight: Dp? = null
) = FormDropdownField(
  fieldName = PresentationText.Resource(R.string.car_modifications),
  items = items.map { it.name },
  selectedName = selected?.name,
  isShowing = isShowing,
  isDisabled = isDisabled,
  onMenuClick = onMenuClick,
  onItemClick = { index ->
    onItemClick(items[index])
  },
  onDismissRequest = onDismissRequest,
  maxHeight = maxHeight,
  hint = PresentationText.Resource(id = R.string.car_modifications)
)


@Preview(
  name = "Modifications Dropdown Menu",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ModificationsDropdownMenuPreview() {
  val items = listOf(
    CarModification(
      id = 1,
      name = "first",
      yearFrom = "year from",
      yearTo = "year to",
      constructionType = "construction type",
      cylinderCapacityCcm = 1,
      fuelType = "fuel type",
      fullName = "full name",
      manufacturer = "manufacturer",
      manufacturerId = 1,
      model = "modelName",
      modelId = 1,
      motorCodes = "motorCodes",
      powerHP = 1,
      powerKW = 1
    ),
    CarModification(
      id = 1,
      name = "second",
      yearFrom = "year from",
      yearTo = "year to",
      constructionType = "construction type",
      cylinderCapacityCcm = 1,
      fuelType = "fuel type",
      fullName = "full name",
      manufacturer = "manufacturer",
      manufacturerId = 1,
      model = "modelName",
      modelId = 1,
      motorCodes = "motorCodes",
      powerHP = 1,
      powerKW = 1
    ),
  )

  val selected = remember { mutableStateOf(items[0]) }
  val isShowing = remember { mutableStateOf(true) }

  ModificationsDropdownMenu(
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