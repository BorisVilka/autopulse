package ru.autopulse05.android.feature.laximo.presentation.categories.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.laximo.presentation.categories.util.LaximoCategoryData

@Composable
fun CategoryBasicItem(
  category: LaximoCategoryData.Basic,
  color: Color = MaterialTheme.colors.surface,
  shape: Shape = MaterialTheme.shapes.small,
  onClick: (LaximoCategoryData.Basic) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(shape = shape, color = color)
      .padding(SpaceSmall)
      .clickable {
        onClick(category)
      }
  ) {
    Text(text = category.name)
  }
}

@Preview(
  name = "Category Basic Item",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun CategoryBasicItemPreview() {
  CategoryBasicItem(
    category =  LaximoCategoryData.Basic(
      id = 2,
      name = "ДВИГАТЕЛЬ",
      code = "code",
      ssd = "ssd"
    ),
    onClick = {}
  )
}