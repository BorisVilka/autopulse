package ru.autopulse05.android.feature.product.presentation.list.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun PriceFilterSection(
  onPriceAscendingFilter: () -> Unit,
  onPriceDescendingFilter: () -> Unit,
  isShowing: Boolean,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .alpha(if (isShowing) 1f else 0f)
      .zIndex(if (isShowing) 10f else -10f)
      .shadow(elevation = SpaceSmall)
  ) {
    PriceFilterTypeItem(
      title = PresentationText.Resource(R.string.by_ascending),
      onClick = { onPriceAscendingFilter() }
    )

    Divider(
      color = MaterialTheme.colors.background,
      modifier = Modifier.width(200.dp)
    )

    PriceFilterTypeItem(
      title = PresentationText.Resource(R.string.by_descending),
      onClick = { onPriceDescendingFilter() }
    )
  }
}

@Preview(
  name = "Price Filter Section Section",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PriceFilterSectionPreview() {
  PriceFilterSection(
    isShowing = true,
    onPriceAscendingFilter = {},
    onPriceDescendingFilter = {}
  )
}

@Preview(
  name = "Price Filter Section Hidden",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PriceFilterSectionHiddenPreview() {
  PriceFilterSection(
    isShowing = false,
    onPriceAscendingFilter = {},
    onPriceDescendingFilter = {}
  )
}