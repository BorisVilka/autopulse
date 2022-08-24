package ru.autopulse05.android.feature.user.presentation.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.order.domain.model.Order
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun OrderItem(
  item: Order,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .shadow(elevation = SpaceSmall)
      .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
      .padding(SpaceNormal)
      .fillMaxWidth()
  ) {
    Text(text = item.number)

    Spacer(modifier = Modifier.height(SpaceNormal))

    Text(
      text = PresentationText.Dynamic("Дата: ${item.date}").asString()
    )
  }
}

@Preview(
  name = "Order Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun OrderItemPreview() {
  OrderItem(
    item = Order(
      number = "number",
      positionsQuantity = "positionsQuantity",
      deliveryAddressId = "deliveryAddressId",
      deliveryAddress = "deliveryAddress",
      deliveryOfficeId = "deliveryOfficeId",
      deliveryOffice = "deliveryOffice",
      deliveryTypeId = "deliveryTypeId",
      deliveryType = "deliveryType",
      paymentTypeId = "paymentTypeId",
      paymentType = "paymentType",
      deliveryCost = "deliveryCost",
      shipmentDate = "shipmentDate",
      sum = "sum",
      date = "date",
      debt = "debt",
      comment = "comment",
      clientOrderNumber = "clientOrderNumber",
      positions = listOf()
    )
  )
}