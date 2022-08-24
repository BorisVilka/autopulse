package ru.autopulse05.android.feature.order.presentation.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.cart.domain.model.Position
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun DetailItem(
  item: Position,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .shadow(elevation = SpaceSmall)
      .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
      .padding(SpaceNormal)
      .fillMaxWidth()
  ) {
    BrandNumberSection(brand = item.brand, number = item.number)

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

