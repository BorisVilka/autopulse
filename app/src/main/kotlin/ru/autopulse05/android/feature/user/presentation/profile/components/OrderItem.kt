package ru.autopulse05.android.feature.user.presentation.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.order.domain.model.Order
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun OrderItem(
  item: Order,
  modifier: Modifier = Modifier,
  onClick: (Order) -> Unit = {}
) {
  Column(
    modifier = modifier
      .shadow(elevation = SpaceSmall)
      .padding(SpaceNormal)
      .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
      .padding(SpaceNormal)
      .clickable { onClick(item) }
      .fillMaxWidth()
  ) {
    Text(text = item.number.orEmpty())

    Spacer(modifier = Modifier.height(SpaceNormal))

    Text(
      text = PresentationText.Dynamic("Дата: ${item.date}").asString()
    )
  }
}
