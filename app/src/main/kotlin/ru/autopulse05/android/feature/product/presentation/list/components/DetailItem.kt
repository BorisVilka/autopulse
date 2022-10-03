package ru.autopulse05.android.feature.product.presentation.list.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.*
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.presentation.components.CartSection
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection
import ru.autopulse05.android.shared.presentation.util.PresentationText
import kotlin.math.min

@Composable
fun DetailItem(
  onOpenProductCardClick: () -> Unit,
  onOpenProductDetails: () -> Unit,
  onAddToCartClick: () -> Unit,
  onIncreaseProductCountClick: () -> Unit,
  onDecreaseProductCountClick: () -> Unit,
  onDeliveryDialogDismiss: () -> Unit,
  onOpenInfoDialog: () -> Unit,
  onDismissInfoDialog: () -> Unit,
  onDeliveryProbabilityButtonClick: () -> Unit,
  onShowBasketClick: () -> Unit,
  onDismissBasketDialog: () -> Unit,
  goToBasket: () -> Unit,
  isShowing: Boolean,
  showBasket: Boolean,
  showInfo: Boolean,
  isShowingBasket: Boolean,
  quantity: Int,
  product: Product
) {

  val colors = listOf("#BFF0C5", "#6CEDFE", "#CCF37A", "#84FDE4", "#01FF0B", "#77EF5E", "#84CEFF")

  Column(
    modifier = Modifier
      .padding(SpaceSmall)
      .shadow(elevation = SpaceSmall)
      .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
      .padding(SpaceNormal)
      .fillMaxWidth()
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .clickable { onOpenProductDetails() }
          .background(color = MaterialTheme.colors.surface)
      ) {
        Text(
          text = product.brand,
          color = Color.BrandYellow,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp
        )

        Spacer(modifier = Modifier.width(SpaceNormal))

        Text(
          text = product.number, color = Color.BrandYellow,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp
        )
      }

      IconButton(
        onClick = {
          onOpenInfoDialog()
        }
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
          contentDescription = PresentationText.Resource(R.string.delivery_probability)
            .asString(),
          modifier = Modifier
            .size(24.dp)
            .zIndex(10f),
        )
      }
    }

    Text(
      text = product.description,
      style = MaterialTheme.typography.body2,
      modifier = Modifier,
      fontSize = 16.sp
    )

    if (colors.contains(product.supplierColor)) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = with(AnnotatedString.Builder()) {
            append(PresentationText.Resource(R.string.warehouse).asString() + " ")
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
            append(PresentationText.Resource(R.string.delivery_deadline).asString() + " ")
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
        Card() {
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
    }



    if (isShowingBasket) {
      Dialog(
        onDismissRequest = { onDismissBasketDialog() }
      ) {
        Card() {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              .background(color = MaterialTheme.colors.background)
              .padding(SpaceNormal)
          ) {

            BrandNumberSection(brand = product.brand, number = product.number)

            Spacer(modifier = Modifier.height(SpaceNormal))

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
                quantity = min(quantity,product.availability),
                onAddToCartClick = {
                  onAddToCartClick()
                  //onDismissBasketDialog()
                },
                onIncreaseProductCountClick = {
                  onIncreaseProductCountClick()
                },
                onDecreaseProductCountClick = {
                  onDecreaseProductCountClick()
                }
              )
            }

            Spacer(modifier = Modifier.height(SpaceNormal))

            BigButton(
              onClick = {
                onAddToCartClick()
                goToBasket()
              },
              text = PresentationText.Dynamic("Перейти в корзину")
            )
          }
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

      if (showBasket) Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
      ) {
        IconButton(
          onClick = { onShowBasketClick() },
          modifier = Modifier.size(SpaceLarge)
        ) {
          Icon(
            painter = painterResource(id = R.mipmap.basket_foreground),
            contentDescription = stringResource(id = R.string.add_to_cart),
            tint = Color.BrandYellow,
            modifier = Modifier.width(25.dp).height(25.dp)
          )
        }

        Spacer(modifier = Modifier.width(SpaceSmall))
      }

      if(showInfo) {
        Dialog(onDismissRequest = { onDismissInfoDialog() }) {
            Card() {
              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                  .background(color = MaterialTheme.colors.background)
                  .padding(SpaceNormal)
              ) {
                BigButton(onClick = {
                  onOpenProductCardClick()
                }, text = PresentationText.Dynamic("Применимость"))
                Spacer(modifier = Modifier.height(SpaceNormal))
                BigButton(onClick = {
                  onOpenProductDetails()
                }, text = PresentationText.Dynamic("Информация"))
              }
            }
        }
      }
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
    showBasket = true,
    isShowingBasket = false,
    onShowBasketClick = {},
    onDismissBasketDialog = {},
    goToBasket = {},
    onOpenProductDetails = {},
    showInfo = false,
    onOpenInfoDialog = {},
    onDismissInfoDialog = {}
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
    onShowBasketClick = {},
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
    showBasket = true,
    isShowingBasket = false,
    onDismissBasketDialog = {},
    goToBasket = {},
    onOpenProductDetails = {},
    showInfo = false,
    onOpenInfoDialog = {},
    onDismissInfoDialog = {}
    )
}