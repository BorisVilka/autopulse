package ru.autopulse05.android.feature.product.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.components.QuantitySelector

@Composable
fun CartSection(
  onAddToCartClick: () -> Unit,
  onIncreaseProductCountClick: () -> Unit,
  onDecreaseProductCountClick: () -> Unit,
  quantity: Int,
  modifier: Modifier = Modifier
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
  ) {
    QuantitySelector(
      onIncreaseProductCountClick = onIncreaseProductCountClick,
      onDecreaseProductCountClick = onDecreaseProductCountClick,
      quantity = quantity
    )

    Spacer(modifier = Modifier.width(SpaceNormal))

      IconButton(
          onClick = { onAddToCartClick() },
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
}

@Preview(
  name = "Cart Section",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CartSectionPreview() {
  CartSection(
    onAddToCartClick = { },
    onIncreaseProductCountClick = { },
    onDecreaseProductCountClick = { },
    quantity = 1
  )
}