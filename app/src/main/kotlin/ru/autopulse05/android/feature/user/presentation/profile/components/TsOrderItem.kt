package ru.autopulse05.android.feature.user.presentation.profile.components

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.feature.order.domain.model.TsOrder

@Composable
fun TsOrderItem(
  order: TsOrder,
) {

  Text(text = order.id)

  Text(text = order.number)

  Text(text = order.agreementId)

  Text(text = order.managerId)

  Text(text = order.createTime)

  Text(text = order.updateTime)
}

@Preview(
  name = "Ts Order Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TsOrderItemPreview() {
  TsOrderItem(
    order = TsOrder(
      id = "id",
      number = "number",
      agreementId = "agreementId",
      managerId = "managerId",
      createTime = "createTime",
      updateTime = "updateTime",
    )
  )
}