package ru.autopulse05.android.feature.laximo.presentation.catalogs.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.InputField
import ru.autopulse05.android.shared.presentation.util.FormTextFieldData
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun SearchByVinOrFrameSection(
  vin: FormTextFieldData,
  onVinChange: (String) -> Unit,
  frameName: FormTextFieldData,
  onFrameNameChange: (String) -> Unit,
  frameNumber: FormTextFieldData,
  onFrameNumberChange: (String) -> Unit,
  onSearchByVinClick: () -> Unit,
  onSearchByFrameClick: () -> Unit,
  modifier: Modifier = Modifier
) {

  Column(
    modifier = modifier
      .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
      .padding(SpaceNormal)
  ) {
    Text(
      text = PresentationText.Resource(R.string.search_by_vin).asString(),
      style = MaterialTheme.typography.h1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    InputField(
      value = vin.value,
      onValueChange = onVinChange,
      hint = PresentationText.Resource(R.string.vin).asString(),
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    BigButton(
      onClick = { onSearchByVinClick() },
      text = PresentationText.Resource(R.string.search)
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    Text(
      text = PresentationText.Resource(R.string.search_by_carcase_code_number).asString(),
      style = MaterialTheme.typography.h1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    InputField(
      value = frameName.value,
      onValueChange = onFrameNameChange,
      hint = PresentationText.Resource(R.string.carcase_code).asString()
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    InputField(
      value = frameNumber.value,
      onValueChange = onFrameNumberChange,
      hint = PresentationText.Resource(R.string.carcase_number).asString()
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    BigButton(
      onClick = { onSearchByFrameClick() },
      text = PresentationText.Resource(R.string.search)
    )
  }
}

@Preview(
  name = "Search By Vin Or Frame Section",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SearchByVinOrFrameSectionPreview() {
  SearchByVinOrFrameSection(
    vin = FormTextFieldData(),
    onVinChange = {},
    frameName = FormTextFieldData(),
    onFrameNameChange = {},
    frameNumber = FormTextFieldData(),
    onFrameNumberChange = {},
    onSearchByVinClick = {},
    onSearchByFrameClick = {}
  )
}