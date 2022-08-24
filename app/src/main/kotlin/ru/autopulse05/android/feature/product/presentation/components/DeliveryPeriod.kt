package ru.autopulse05.android.feature.product.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun DeliveryPeriod(
  deliveryPeriod: String,
  deliveryPeriodMax: String,
  deadlineReplace: String,
  supplierColor: String,
  modifier: Modifier = Modifier
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
  ) {
    Text(
      text = buildString {
        if (deliveryPeriod.toInt() == 0) {
          append(
            PresentationText.Resource(R.string.delivery_deadline)
              .asString() + " " + deliveryPeriod.toInt() / 24
          )
        } else {
          append(
            PresentationText.Resource(R.string.delivery_deadline_from)
              .asString() + " " + deliveryPeriod.toInt() / 24
          )
        }
        if (deliveryPeriodMax.isNotBlank()) {
          append(
            " " + PresentationText.Resource(R.string.to)
              .asString() + " " + deliveryPeriodMax.toInt() / 24
          )
        } else if (deadlineReplace.isNotBlank()) {
          append(
            " " + PresentationText.Resource(R.string.to)
              .asString() + " " + deadlineReplace.toInt() / 24
          )
        }
        append(" " + PresentationText.Resource(R.string.days).asString())
      }
    )

    Spacer(modifier = Modifier.width(SpaceSmall))

    Icon(
      painter = painterResource(R.drawable.ic_truck),
      contentDescription = PresentationText.Resource(R.string.supplier_color).asString(),
      modifier = Modifier.zIndex(10f),
      tint = Color(android.graphics.Color.parseColor("#$supplierColor"))
    )
  }
}

@Preview(
  name = "Delivery Period",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DeliveryPeriodPreview() {
  DeliveryPeriod(
    deliveryPeriod = "deliveryPeriod",
    deliveryPeriodMax = "deliveryPeriodMax",
    deadlineReplace = "deadlineReplace",
    supplierColor = "supplierColor"
  )
}