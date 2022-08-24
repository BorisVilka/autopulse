package ru.autopulse05.android.feature.laximo.presentation.categories.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.laximo.presentation.categories.util.LaximoCategoryData
import ru.autopulse05.android.shared.presentation.components.ExpandableCard
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun CategoryExpandableItem(
  category: LaximoCategoryData.Expandable,
  onBasicClick: (LaximoCategoryData.Basic) -> Unit,
  color: Color = MaterialTheme.colors.surface,
  shape: Shape = MaterialTheme.shapes.small,
  backgroundColor: Color = MaterialTheme.colors.secondary,
  elevationEnabled: Boolean = true,
  onExpandableClick: (LaximoCategoryData.Expandable) -> Unit
) {
  ExpandableCard(
    title = PresentationText.Dynamic(category.name),
    onClick = { onExpandableClick(category) },
    expanded = category.expanded,
    elevationEnabled = elevationEnabled,
    shape = shape,
    contentExpandedBackgroundColor = color,
    contentCollapsedBackgroundColor = color
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(backgroundColor)
        .padding(
          start = SpaceSmall
        )
    ) {
      category.childrens.forEach { children ->
        CategoryItem(
          category = children,
          onBasicClick = onBasicClick,
          elevationEnabled = false,
          backgroundColor = Color.Transparent,
          shape = RoundedCornerShape(0.dp),
          color = Color.Transparent,
          onExpandableClick = onExpandableClick
        )
      }
    }
  }
}

@Preview(
  name = "Category Expandable Item",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun CategoryExpandableItemPreview() {
  CategoryExpandableItem(
    category = LaximoCategoryData.Expandable(
      id = 1,
      name = "ДВИГАТЕЛЬ И ПОДВЕСКИ",
      expanded = false,
      childrens = listOf(
        LaximoCategoryData.Expandable(
          id = 2,
          name = "ДВИГАТЕЛЬ",
          expanded = false,
          childrens = listOf(
            LaximoCategoryData.Basic(
              id = 2,
              name = "ДВИГАТЕЛЬ",
              code = "code",
              ssd = "ssd"
            )
          ),
          code = "code",
          ssd = "ssd"
        ),
        LaximoCategoryData.Basic(
          id = 2,
          name = "ВАЛЫ И МУФТЫ",
          code = "code",
          ssd = "ssd"
        )
      ),
      code = "code",
      ssd = "ssd"
    ),
    onBasicClick = {},
    onExpandableClick = {}
  )
}

@Preview(
  name = "Category Expandable Item Expanded",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true
)
@Composable
fun CategoryExpandableItemExpandedPreview() {
  CategoryExpandableItem(
    category = LaximoCategoryData.Expandable(
      id = 1,
      name = "ДВИГАТЕЛЬ И ПОДВЕСКИ",
      expanded = true,
      childrens = listOf(
        LaximoCategoryData.Expandable(
          id = 2,
          name = "ДВИГАТЕЛЬ",
          expanded = false,
          childrens = listOf(
            LaximoCategoryData.Basic(
              id = 2,
              name = "ДВИГАТЕЛЬ",
              code = "code",
              ssd = "ssd"
            )
          ),
          code = "code",
          ssd = "ssd"
        ),
        LaximoCategoryData.Basic(
          id = 2,
          name = "ВАЛЫ И МУФТЫ",
          code = "code",
          ssd = "ssd"
        )
      ),
      code = "code",
      ssd = "ssd"
    ),
    onBasicClick = {},
    onExpandableClick = {}
  )
}