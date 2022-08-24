package ru.autopulse05.android.feature.laximo.presentation.vehicle_search.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicleFormFieldOption
import ru.autopulse05.android.shared.presentation.components.FormDropdownField
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun VehicleSearchDropdownMenu(
  text: PresentationText,
  items: List<LaximoVehicleFormFieldOption>,
  selected: LaximoVehicleFormFieldOption?,
  isShowing: Boolean,
  onMenuClick: () -> Unit,
  onItemClick: (LaximoVehicleFormFieldOption) -> Unit,
  onDismissRequest: () -> Unit,
  isDisabled: Boolean = false
) {
  FormDropdownField(
    fieldName = text,
    items = items.map { it.value },
    selectedName = selected?.value,
    isShowing = isShowing,
    onMenuClick = onMenuClick,
    isDisabled = isDisabled,
    onItemClick = { index -> onItemClick(items[index]) },
    onDismissRequest = onDismissRequest,
    hint = text
  )
}

@Preview(
  name = "Vehicle Search Dropdown Menu",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun VehicleSearchDropdownMenuPreview() {

  VehicleSearchDropdownMenu(
    text = PresentationText.Dynamic("text"),
    items = listOf(),
    selected = null,
    isShowing = false,
    onMenuClick = {},
    onItemClick = {},
    onDismissRequest = {}
  )
}