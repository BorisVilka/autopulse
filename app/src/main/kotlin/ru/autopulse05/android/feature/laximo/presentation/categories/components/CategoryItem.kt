package ru.autopulse05.android.feature.laximo.presentation.categories.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.feature.laximo.presentation.categories.util.LaximoCategoryData

@Composable
fun CategoryItem(
  category: LaximoCategoryData,
  elevationEnabled: Boolean = true,
  color: Color = Color.BrandYellow,
  backgroundColor: Color = MaterialTheme.colors.surface,
  shape: Shape = MaterialTheme.shapes.small,
  onBasicClick: (LaximoCategoryData.Basic) -> Unit,
  onExpandableClick: (LaximoCategoryData.Expandable) -> Unit
) {
  when (category) {
    is LaximoCategoryData.Expandable -> CategoryExpandableItem(
      category = category,
      elevationEnabled = elevationEnabled,
      shape = shape,
      color = color,
      backgroundColor = backgroundColor,
      onExpandableClick = onExpandableClick,
      onBasicClick = onBasicClick
    )
    is LaximoCategoryData.Basic -> CategoryBasicItem(
      category = category,
      shape = shape,
      color = color,
      onClick = onBasicClick
    )
  }
}