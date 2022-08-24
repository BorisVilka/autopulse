package ru.autopulse05.android.feature.user.presentation.profile.components

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.feature.order.domain.model.Refund

@Composable
fun RefundItem(
  refund: Refund,
) {

  Text(text = refund.id)

  Text(text = refund.opId)

  Text(text = refund.status.toString())

  Text(text = refund.type.toString())

  Text(text = refund.orderPickingGoodId)

  Text(text = refund.oldItemId)

  Text(text = refund.itemId)

  Text(text = refund.quantity)

  Text(text = refund.attrs)

  Text(text = refund.product)

  Text(text = refund.pickingDate)

  Text(text = refund.comment)

  Text(text = refund.orderPickingGood)

  Text(text = refund.orderPicking)

  Text(text = refund.availableQuantity)

}

@Preview(
  name = "Refund Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RefundItemPreview() {
  RefundItem(
    refund = Refund(
      id = "id",
      opId = "opId",
      status = 0,
      type = 0,
      orderPickingGoodId = "orderPickingGoodId",
      oldItemId = "oldItemId",
      itemId = "itemId",
      quantity = "quantity",
      attrs = "attrs",
      product = "product",
      pickingDate = "pickingDate",
      comment = "comment",
      orderPickingGood = "orderPickingGood",
      orderPicking = "orderPicking",
      availableQuantity = "availableQuantity"
    )
  )
}