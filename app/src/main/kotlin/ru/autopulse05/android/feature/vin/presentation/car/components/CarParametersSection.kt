package ru.autopulse05.android.feature.vin.presentation.car.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraSmall
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.feature.vin.presentation.util.VinCarMode
import ru.autopulse05.android.shared.presentation.components.ExpandableUpCard
import ru.autopulse05.android.shared.presentation.components.FormDropdownField
import ru.autopulse05.android.shared.presentation.components.FormTextField
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import ru.autopulse05.android.shared.presentation.util.FormTextFieldData
import ru.autopulse05.android.shared.presentation.util.PresentationText

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun CarParametersSection(
  mode: VinCarMode,
  onModeChange: (VinCarMode) -> Unit,
  vin: FormTextFieldData,
  onVinChange: (String) -> Unit,
  frameNumber: FormTextFieldData,
  onFrameNumberChange: (String) -> Unit,
  mark: FormSelectorFieldData<CarMark>,
  onMarkChange: (CarMark) -> Unit,
  onMarksVisibilityChange: (Boolean) -> Unit,
  year: FormSelectorFieldData<Int>,
  onYearChange: (Int) -> Unit,
  onYearsVisibilityChange: (Boolean) -> Unit,
  model: FormSelectorFieldData<CarModel>,
  onModelChange: (CarModel) -> Unit,
  onModelsVisibilityChange: (Boolean) -> Unit,
  modification: FormSelectorFieldData<CarModification>,
  onModificationChange: (CarModification) -> Unit,
  onModificationsVisibilityChange: (Boolean) -> Unit,
  isAdvancedParametersVisible: Boolean,
  onAdvancedParametersVisibilityChange: (Boolean) -> Unit
) = ExpandableUpCard(
  title = PresentationText.Dynamic(
    if (isAdvancedParametersVisible) "Не хочу указывать модель" else "Хочу выбрать модель"
  ),
  onClick = { value ->
    onAdvancedParametersVisibilityChange(value)
  },
  expanded = isAdvancedParametersVisible,
  shape = MaterialTheme.shapes.small,
  cardExpandedBackgroundColor = MaterialTheme.colors.surface,
  cardCollapsedBackgroundColor = MaterialTheme.colors.surface,
  visibleContent = {
    Column(modifier = Modifier.padding(SpaceSmall)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
          selected = (VinCarMode.VIN == mode),
          colors = RadioButtonDefaults.colors(selectedColor = Color.BrandYellow),
          onClick = { onModeChange(VinCarMode.VIN) }
        )

        Text(
          text = PresentationText.Dynamic("VIN").asString(),
          style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.width(SpaceSmall))

        RadioButton(
          selected = (VinCarMode.FRAME_NUMBER == mode),
          colors = RadioButtonDefaults.colors(selectedColor = Color.BrandYellow),
          onClick = { onModeChange(VinCarMode.FRAME_NUMBER) }
        )

        Text(
          text = PresentationText.Dynamic("Номер кузова").asString(),
          style = MaterialTheme.typography.body1
        )
      }

      Spacer(modifier = Modifier.width(SpaceExtraSmall))

      if (mode == VinCarMode.VIN) FormTextField(
        fieldData = vin,
        hint = PresentationText.Dynamic("VIN"),
        onValueChange = onVinChange
      ) else FormTextField(
        fieldData = frameNumber,
        hint = PresentationText.Dynamic("Номер кузова"),
        onValueChange = onFrameNumberChange
      )

      Spacer(modifier = Modifier.height(SpaceSmall))

      FormDropdownField(
        items = mark.items.map { it.name.orEmpty() },
        selectedName = mark.value?.name,
        isShowing = mark.isShowing,
        isDisabled = mark.isDisabled,
        onMenuClick = { onMarksVisibilityChange(true) },
        onItemClick = { index -> onMarkChange(mark.items[index]) },
        onDismissRequest = { onMarksVisibilityChange(false) },
        hint = PresentationText.Resource(R.string.car_mark)
      )
    }
  },
  content = {
    Column(modifier = Modifier.padding(SpaceSmall)) {
      Spacer(modifier = Modifier.height(SpaceExtraSmall))

      FormDropdownField(
        items = year.items.map { it.toString() },
        selectedName = year.value?.toString(),
        isShowing = year.isShowing,
        isDisabled = year.isDisabled,
        onMenuClick = { onYearsVisibilityChange(true) },
        onItemClick = { index -> onYearChange(year.items[index]) },
        onDismissRequest = { onYearsVisibilityChange(false) },
        hint = PresentationText.Resource(R.string.year)
      )

      Spacer(modifier = Modifier.height(SpaceExtraSmall))

      FormDropdownField(
        items = model.items.map { it.name.orEmpty() },
        selectedName = model.value?.name,
        isShowing = model.isShowing,
        isDisabled = model.isDisabled,
        onMenuClick = { onModelsVisibilityChange(true) },
        onItemClick = { index -> onModelChange(model.items[index]) },
        onDismissRequest = { onModelsVisibilityChange(false) },
        hint = PresentationText.Resource(R.string.model)
      )

      Spacer(modifier = Modifier.height(SpaceExtraSmall))

      FormDropdownField(
        items = modification.items.map { it.name.orEmpty() },
        selectedName = modification.value?.name,
        isShowing = modification.isShowing,
        isDisabled = modification.isDisabled,
        onMenuClick = { onModificationsVisibilityChange(true) },
        onItemClick = { index -> onModificationChange(modification.items[index]) },
        onDismissRequest = { onModificationsVisibilityChange(false) },
        hint = PresentationText.Resource(R.string.car_modifications)
      )
    }
  },
)


@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Car Parameters Section",
  showBackground = true
)
@Composable
fun CarParametersSectionPreview() {
  CarParametersSection(
    mode = VinCarMode.VIN,
    onModeChange = {},
    vin = FormTextFieldData(),
    onVinChange = {},
    frameNumber = FormTextFieldData(),
    onFrameNumberChange = {},
    mark = FormSelectorFieldData(),
    onMarkChange = {},
    onMarksVisibilityChange = {},
    year = FormSelectorFieldData(),
    onYearChange = {},
    onYearsVisibilityChange = {},
    model = FormSelectorFieldData(),
    onModelChange = {},
    onModelsVisibilityChange = {},
    modification = FormSelectorFieldData(),
    onModificationChange = {},
    onModificationsVisibilityChange = {},
    isAdvancedParametersVisible = true,
    onAdvancedParametersVisibilityChange = {},
  )
}