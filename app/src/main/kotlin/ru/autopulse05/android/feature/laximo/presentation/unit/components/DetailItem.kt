package ru.autopulse05.android.feature.laximo.presentation.unit.components

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
import ru.autopulse05.android.feature.laximo.domain.model.LaximoDetail

@Composable
fun DetailItem(
  detail: LaximoDetail,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .clickable { onClick() }
      .shadow(elevation = SpaceSmall)
      .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
      .padding(SpaceNormal)
      .fillMaxWidth()
  ) {
    Text(
      text = detail.name,
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    if (detail.oem != null) {
      Text(text = detail.oem)
    }
  }
}

@Preview(
  name =  "Detail Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DetailItemPreview() {
  DetailItem(
    detail = LaximoDetail(
      codeOnImage = "code on image",
      name = "name"
    ),
    onClick = {}
  )
}