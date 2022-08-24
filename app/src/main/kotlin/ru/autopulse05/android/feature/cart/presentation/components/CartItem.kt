package ru.autopulse05.android.feature.cart.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun CartItem(
  item: CartItem,
  selected: Boolean,
  onSelectChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .shadow(elevation = SpaceSmall)
      .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
      .padding(SpaceNormal)
      .fillMaxWidth()
  ) {
    Row(
      modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      BrandNumberSection(brand = item.brand, number = item.number)

      Checkbox(
        checked = selected,
        onCheckedChange = onSelectChange,
        colors = CheckboxDefaults.colors(checkmarkColor = MaterialTheme.colors.onSecondary)
      )
    }

    Spacer(modifier = Modifier.height(SpaceNormal))

    Text(text = item.description)

    Spacer(modifier = Modifier.height(SpaceNormal))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = PresentationText.Resource(
          id = R.string.price,
          append = " ${item.price}"
        ).asString()
      )

      Text(
        text = PresentationText.Resource(
          id = R.string.quantity,
          append = " ${item.quantity}"
        ).asString()
      )
    }
  }
}

@Preview(
  name = "Basket Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BasketItemPreview() {
  CartItem(
    item = CartItem(
      id = "brand-number",
      brand = "brand",
      comment = "comment",
      deadline = "deadline",
      deadlineMax = "deadline max",
      description = "description",
      itemKey = "item key",
      number = "number",
      numberFix = "number fix",
      packing = 1,
      positionId = 1,
      price = "price",
      priceInSiteCurrency = 1,
      priceRate = "price rate",
      quantity = 1,
      code = "code",
      supplierCode = "supplier code"
    ),
    selected = true,
    onSelectChange = {}
  )
}