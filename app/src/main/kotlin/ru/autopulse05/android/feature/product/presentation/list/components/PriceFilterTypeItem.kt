package ru.autopulse05.android.feature.product.presentation.list.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun PriceFilterTypeItem(
  onClick: () -> Unit,
  title: PresentationText
) {
  Box(
    modifier = Modifier
      .clickable { onClick() }
      .size(width = 200.dp, height = 75.dp)
      .background(color = MaterialTheme.colors.surface)
      .padding(SpaceNormal),
    contentAlignment = Alignment.Center
  ) {
    Text(text = title.asString())
  }
}

@Preview(
  name = "Price Filter Type Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PriceFilterTypeItemPreview() {
  PriceFilterTypeItem(
    title = PresentationText.Resource(R.string.by_descending),
    onClick = {}
  )
}

