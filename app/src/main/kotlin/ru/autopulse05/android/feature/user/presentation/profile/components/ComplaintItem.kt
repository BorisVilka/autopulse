package ru.autopulse05.android.feature.user.presentation.profile.components

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.feature.order.domain.model.Complaint

@Composable
fun ComplaintItem(
  complaint: Complaint,
) {

  Text(text = complaint.id)

  Text(text = complaint.opId)

  Text(text = complaint.status.toString())

  Text(text = complaint.type.toString())

  Text(text = complaint.orderPickingGoodId)

  Text(text = complaint.oldItemId)

  Text(text = complaint.itemId)

  Text(text = complaint.quantity)

  Text(text = complaint.attrs)

  Text(text = complaint.product)

  Text(text = complaint.pickingDate)

  Text(text = complaint.comment)

  Text(text = complaint.orderPickingGood)

  Text(text = complaint.orderPicking)

  Text(text = complaint.availableQuantity)

}

@Preview(
  name = "Complaint Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ComplaintItemPreview() {
  ComplaintItem(
    complaint = Complaint(
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