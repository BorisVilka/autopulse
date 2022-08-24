package ru.autopulse05.android.feature.user.presentation.profile.components

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.feature.order.domain.model.Picking

@Composable
fun PickingItem(
  picking: Picking,
) {

  Text(text = picking.id)

  Text(text = picking.opId)

  Text(text = picking.coPositionId)

  Text(text = picking.oldCoPositionId)

  Text(text = picking.quantity)

  Text(text = picking.itemId)

  Text(text = picking.clToResRate)

  Text(text = picking.sellPrice)

  Text(text = picking.clSellPrice)

  Text(text = picking.product)

  Text(text = picking.operationInfo)

  Text(text = picking.availableQuantityCC)
}

@Preview(
  name = "Picking Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PickingItemPreview() {
  PickingItem(
    picking = Picking(
      id = "id",
      opId = "opId",
      coPositionId = "coPositionId",
      oldCoPositionId = "oldCoPositionId",
      quantity = "quantity",
      itemId = "itemId",
      clToResRate = "clToResRate",
      sellPrice = "sellPrice",
      clSellPrice = "clSellPrice",
      product = "product",
      operationInfo = "operationInfo",
      availableQuantityCC = "availableQuantityCC",
    )
  )
}