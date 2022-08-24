package ru.autopulse05.android.feature.product.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal

@Composable
fun AddToCartSection(
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
    IconButton(
      onClick = { onDecreaseProductCountClick() },
      modifier = Modifier.size(SpaceLarge)
    ) {
      Text(text = "<", color = Color.BrandYellow, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }

    Text(
      text = quantity.toString(),
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(horizontal = SpaceLarge)
    )

    IconButton(
      onClick = { onIncreaseProductCountClick() },
      modifier = Modifier.size(SpaceLarge)
    ) {
      Text(text = ">", color = Color.BrandYellow, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.width(SpaceNormal))

    IconButton(
      onClick = { onAddToCartClick() },
      modifier = Modifier.size(SpaceLarge)
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_cart_outlined),
        contentDescription = stringResource(id = R.string.add_to_cart),
        tint = Color.BrandYellow
      )
    }
  }
}

@Preview(
  name = "Add To Cart Section",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AddToCartSectionPreview() {
  AddToCartSection(
    onAddToCartClick = { },
    onIncreaseProductCountClick = { },
    onDecreaseProductCountClick = { },
    quantity = 1
  )
}