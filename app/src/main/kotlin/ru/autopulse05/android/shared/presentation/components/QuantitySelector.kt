package ru.autopulse05.android.shared.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge

@Composable
fun QuantitySelector(
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
  }
}

@Preview(
  name = "Quantity Selector",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun QuantitySelectorPreview() {
  QuantitySelector(
    onIncreaseProductCountClick = { },
    onDecreaseProductCountClick = { },
    quantity = 1
  )
}