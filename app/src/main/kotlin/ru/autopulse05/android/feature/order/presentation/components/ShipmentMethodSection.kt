package ru.autopulse05.android.feature.order.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.shipment.domain.model.ShipmentMethod
import ru.autopulse05.android.shared.presentation.components.RadioButtonWithTextList
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ShipmentMethodSection(
  modifier: Modifier = Modifier,
  value: ShipmentMethod? = null,
  items: List<ShipmentMethod>,
  onSelect: (ShipmentMethod) -> Unit
) {
  Column(modifier = modifier) {
    Text(
      text = PresentationText.Dynamic("Метод доставки").asString(),
      modifier = Modifier
        .padding(
          top = SpaceNormal,
          bottom = SpaceSmall
        ),
      style = MaterialTheme.typography.subtitle1
    )

    RadioButtonWithTextList(
      values = items.map { Pair(PresentationText.Dynamic(it.name), it) },
      onClick = onSelect,
      selectedValue = value
    )
  }
}

@Preview(
  name = "Shipment Method Section",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ShipmentMethodSectionPreview() {
  ShipmentMethodSection(
    value = null,
    items = listOf(),
    onSelect = {}
  )
}