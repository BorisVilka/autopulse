package ru.autopulse05.android.feature.product.presentation.list.components

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.core.presentation.ui.theme.BrandDarkGray
import ru.autopulse05.android.core.presentation.ui.theme.BrandGreen

@Composable
fun PieChart(
  points: List<Float>,
) {
  val total = points.sum()

  val proportions = points.map {
    it * 100 / total
  }

  val sweepAnglePercentage = proportions.map {
    360 * it / 100
  }

  Canvas(
    modifier = Modifier.size(200.dp, 200.dp)
  ) {
    var startAngel = 270f

    drawArc(
      color = Color.BrandGreen,
      startAngle = startAngel,
      sweepAngle = sweepAnglePercentage[0],
      useCenter = true,
      size = Size(width = size.width, height = size.width)
    )

    startAngel += sweepAnglePercentage[0]

    drawArc(
      color = Color.BrandDarkGray,
      startAngle = startAngel,
      sweepAngle = sweepAnglePercentage[1],
      useCenter = true,
      size = Size(width = size.width, height = size.width)
    )
  }
}

@Preview(
  name = "Pie Chart",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PieChartPreview() {
  PieChart(
    points = listOf()
  )
}