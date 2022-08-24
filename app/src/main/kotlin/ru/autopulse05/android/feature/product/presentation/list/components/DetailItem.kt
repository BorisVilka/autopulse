package ru.autopulse05.android.feature.product.presentation.list.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.*
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.presentation.components.CartSection
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun DetailItem(
  onOpenProductCardClick: () -> Unit,
  onAddToCartClick: () -> Unit,
  onIncreaseProductCountClick: () -> Unit,
  onDecreaseProductCountClick: () -> Unit,
  onDeliveryDialogDismiss: () -> Unit,
  onDeliveryProbabilityButtonClick: () -> Unit,
  isShowing: Boolean,
  showBasket: Boolean,
  quantity: Int,
  product: Product
) {

  val colors = listOf("#BFF0C5", "#6CEDFE", "#CCF37A", "#84FDE4", "#01FF0B", "#77EF5E", "#84CEFF")

  Column(
    modifier = Modifier.padding(SpaceNormal)
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      BrandNumberSection(
        brand = product.brand,
        number = product.number,
        modifier = Modifier
          .clickable { onOpenProductCardClick() }
          .background(color = MaterialTheme.colors.surface)
          .padding(SpaceSmall)
      )

      Row {
        IconButton(
          onClick = {}
        ) {
          Icon(
            painter = painterResource(id = R.drawable.ic_wheel),
            contentDescription = PresentationText.Resource(R.string.availability).asString(),
            tint = Color.BrandYellow,
            modifier = Modifier.size(32.dp)
          )
        }

        Spacer(modifier = Modifier.width(SpaceNormal))

        IconButton(
          onClick = { onOpenProductCardClick() }
        ) {
          Icon(
            painter = painterResource(id = R.drawable.ic_info),
            contentDescription = PresentationText.Resource(R.string.info).asString(),
            tint = Color.BrandYellow,
            modifier = Modifier.size(32.dp)
          )
        }
      }
    }

    Text(
      text = product.description,
      style = MaterialTheme.typography.body2,
      modifier = Modifier.padding(top = SpaceNormal)
    )

    if (colors.contains(product.supplierColor)) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = with(AnnotatedString.Builder()) {
            withStyle(SpanStyle(color = Color.BrandYellow)) {
              append(PresentationText.Resource(R.string.warehouse).asString() + " ")
            }
            append(
              when (product.supplierColor) {
                "#BFF0C5", "#6CEDFE", "#77EF5E" -> "Махачкала:"
                "#CCF37A", "#84FDE4", "#84CEFF" -> "Пятигорск:"
                "#01FF0B" -> "Ростов-на-Дону:"
                else -> ""
              }
            )
            if (product.deliveryPeriod == 0) {
              append(" ${product.deliveryPeriod}")
            } else {
              append(
                " " + PresentationText.Resource(R.string.from)
                  .asString() + " " + product.deliveryPeriod / 24
              )
            }
            if (product.deliveryPeriodMax.isNotBlank()) {
              append(
                " " + PresentationText.Resource(R.string.to)
                  .asString() + " " + product.deliveryPeriodMax.toInt() / 24
              )
            } else if (product.deadlineReplace.isNotBlank()) {
              append(
                " " + PresentationText.Resource(R.string.to)
                  .asString() + " " + product.deadlineReplace.toInt() / 24
              )
            }
            append(" " + PresentationText.Resource(R.string.days).asString())
            toAnnotatedString()
          }
        )
        IconButton(
          onClick = { onDeliveryProbabilityButtonClick() },
        ) {
          Icon(
            painter = painterResource(id = R.drawable.ic_truck),
            contentDescription = PresentationText.Resource(R.string.delivery_probability)
              .asString(),
            modifier = Modifier
              .size(24.dp)
              .zIndex(10f),
          )
          Icon(
            painter = painterResource(id = R.drawable.ic_truck_filled),
            contentDescription = PresentationText.Resource(R.string.delivery_probability)
              .asString(),
            modifier = Modifier.size(24.dp),
            tint = Color(android.graphics.Color.parseColor(product.supplierColor))
          )
        }
      }
    } else {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = with(AnnotatedString.Builder()) {
            withStyle(SpanStyle(color = Color.BrandYellow)) {
              append(PresentationText.Resource(R.string.delivery_deadline).asString() + " ")
            }
            if (product.deliveryPeriod == 0) {
              append(" ${product.deliveryPeriod}")
            } else {
              append(
                " " + PresentationText.Resource(R.string.from)
                  .asString() + " " + product.deliveryPeriod / 24
              )
            }
            if (product.deliveryPeriodMax.isNotBlank()) {
              append(
                " " + PresentationText.Resource(R.string.to)
                  .asString() + " " + product.deliveryPeriodMax.toInt() / 24
              )
            } else if (product.deadlineReplace.isNotBlank()) {
              append(
                " " + PresentationText.Resource(R.string.to)
                  .asString() + " " + product.deadlineReplace.toInt() / 24
              )
            }
            append(" " + PresentationText.Resource(R.string.days).asString())
            toAnnotatedString()
          }
        )
        IconButton(
          onClick = { onDeliveryProbabilityButtonClick() },
        ) {
          Icon(
            painter = painterResource(id = R.drawable.ic_truck),
            contentDescription = PresentationText.Resource(R.string.delivery_probability)
              .asString(),
            modifier = Modifier
              .size(24.dp)
              .zIndex(10f),
          )
          Icon(
            painter = painterResource(id = R.drawable.ic_truck_filled),
            contentDescription = PresentationText.Resource(R.string.delivery_probability)
              .asString(),
            modifier = Modifier.size(24.dp),
            tint = Color(android.graphics.Color.parseColor("#${product.supplierColor}"))
          )
        }
      }
    }

    if (isShowing) {
      Dialog(
        onDismissRequest = { onDeliveryDialogDismiss() }
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .padding(SpaceNormal)
        ) {
          Text(
            text = PresentationText.Resource(R.string.last_updated)
              .asString() + " " + product.lastUpdateTime
          )

          Spacer(modifier = Modifier.height(SpaceNormal))

          Box {
            PieChart(
              listOf(
                product.deliveryProbability,
                100 - product.deliveryProbability
              )
            )

            Text(
              text = product.deliveryProbability.toInt().toString() + "%",
              modifier = Modifier.align(Alignment.Center)
            )
          }


          Spacer(modifier = Modifier.height(SpaceNormal))

          Spacer(modifier = Modifier.width(SpaceNormal))

          Text(
            text = PresentationText.Resource(R.string.delivery_probability).asString(),
            color = Color.BrandGreen
          )

          Spacer(modifier = Modifier.width(SpaceNormal))

          Text(
            text = PresentationText.Resource(R.string.denie_probability).asString(),
            color = Color.BrandDarkGray
          )

        }
      }
    }

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        Text(
          text = "${product.price} ₽",
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp
        )
        Text(
          text = buildString {
            append("${PresentationText.Resource(R.string.availability).asString()} ")
            append(
              when (product.availability) {
                -1 -> "+"
                -2 -> "++"
                -3 -> "+++"
                -10 -> PresentationText.Resource(R.string.not_available).asString()
                else -> "${product.availability} " + PresentationText.Resource(R.string.pieces)
                  .asString()
              }
            )
          },
          fontSize = 14.sp
        )
      }

      if (showBasket) CartSection(
        quantity = quantity,
        onAddToCartClick = { onAddToCartClick() },
        onIncreaseProductCountClick = { onIncreaseProductCountClick() },
        onDecreaseProductCountClick = { onDecreaseProductCountClick() },
      )
    }
  }
}

@Preview(
  name = "Detail Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DetailItemPreview() {
  DetailItem(
    onOpenProductCardClick = { },
    onAddToCartClick = { },
    onIncreaseProductCountClick = { },
    onDecreaseProductCountClick = { },
    onDeliveryDialogDismiss = { },
    onDeliveryProbabilityButtonClick = { },
    isShowing = true,
    product = Product(
      brand = "brand",
      number = "number",
      numberFix = "numberFix",
      description = "description",
      availability = 3,
      packing = 2,
      deliveryPeriod = 4,
      deliveryPeriodMax = "deliveryPeriodMax",
      deadlineReplace = "deadlineReplace",
      distributorCode = "distributorCode",
      supplierCode = "supplierCode",
      supplierColor = "supplierColor",
//      supplierDescription = "supplierDescription",
      itemKey = "itemKey",
      price = 5.2,
      weight = 2.0,
      deliveryProbability = 2f,
      lastUpdateTime = "lastUpdateTime",
      additionalPrice = 3,
      noReturn = false,
      distributorId = 2,
      code = "",
    ),
    quantity = 1,
    showBasket = true
  )
}

@Preview(
  name = "Detail Item Hidden",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DetailItemHiddenPreview() {
  DetailItem(
    onOpenProductCardClick = { },
    onAddToCartClick = { },
    onIncreaseProductCountClick = { },
    onDecreaseProductCountClick = { },
    onDeliveryDialogDismiss = { },
    onDeliveryProbabilityButtonClick = { },
    isShowing = false,
    product = Product(
      brand = "brand",
      number = "number",
      numberFix = "numberFix",
      description = "description",
      availability = 3,
      packing = 2,
      deliveryPeriod = 4,
      deliveryPeriodMax = "deliveryPeriodMax",
      deadlineReplace = "deadlineReplace",
      distributorCode = "distributorCode",
      supplierCode = "supplierCode",
      supplierColor = "supplierColor",
//      supplierDescription = "supplierDescription",
      itemKey = "itemKey",
      price = 5.2,
      weight = 2.0,
      deliveryProbability = 2f,
      lastUpdateTime = "lastUpdateTime",
      additionalPrice = 3,
      noReturn = false,
      distributorId = 2,
      code = "",
    ),
    quantity = 1,
    showBasket = true
  )
}