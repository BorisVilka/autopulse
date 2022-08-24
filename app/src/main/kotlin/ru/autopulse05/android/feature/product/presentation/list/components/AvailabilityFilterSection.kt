package ru.autopulse05.android.feature.product.presentation.list.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun AvailabilityFilterSection(
  isShowing: Boolean,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .zIndex(if (isShowing) 10f else -10f)
      .alpha(if (isShowing) 1f else 0f)
      .shadow(elevation = SpaceSmall)
      .size(width = 200.dp, height = 75.dp)
      .background(color = MaterialTheme.colors.surface)
      .padding(SpaceNormal),
    contentAlignment = Alignment.Center
  ) {
    Text(text = PresentationText.Resource(R.string.available).asString())
  }
}

@Preview(
  name = "Availability Filter Section",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AvailabilityFilterSectionPreview() {
  AvailabilityFilterSection(
    isShowing = true
  )
}

@Preview(
  name = "Availability Filter Section Hidden",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AvailabilityFilterSectionHiddenPreview() {
  AvailabilityFilterSection(
    isShowing = false
  )
}