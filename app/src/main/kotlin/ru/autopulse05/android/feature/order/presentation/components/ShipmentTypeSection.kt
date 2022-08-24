package ru.autopulse05.android.feature.order.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.order.presentation.util.OrderShipmentMode
import ru.autopulse05.android.shared.presentation.components.RadioButtonWithTextList
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ShipmentTypeSection(
  modifier: Modifier = Modifier,
  selectedMode: OrderShipmentMode,
  onSelect: (OrderShipmentMode) -> Unit
) {
  Column(
    modifier = modifier
  ) {
    Text(
      text = PresentationText.Dynamic("Тип доставки").asString(),
      modifier = Modifier
        .padding(
          top = SpaceNormal,
          bottom = SpaceSmall
        ),
      style = MaterialTheme.typography.subtitle1
    )

    RadioButtonWithTextList(
      values = listOf(
        Pair(PresentationText.Resource(R.string.pickup), OrderShipmentMode.Pickup),
        Pair(PresentationText.Resource(R.string.delivery), OrderShipmentMode.Delivery),
      ),
      onClick = onSelect,
      selectedValue = selectedMode
    )
  }

}

@Preview(
  name = "Shipment Type Section",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ShipmentTypeSectionPreview() {
  ShipmentTypeSection(
    selectedMode = OrderShipmentMode.Delivery,
    onSelect = {}
  )
}