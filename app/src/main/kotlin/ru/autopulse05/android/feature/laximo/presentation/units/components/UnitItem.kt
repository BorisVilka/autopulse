package ru.autopulse05.android.feature.laximo.presentation.units.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.feature.laximo.domain.model.LaximoUnit
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun UnitItem(
  unit: LaximoUnit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val imageWidth = 250

  Card(
    modifier = modifier.clickable { onClick() },
    shape = MaterialTheme.shapes.small,
    backgroundColor = MaterialTheme.colors.surface
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      GlideImage(
        imageModel = unit.imageUrl.replace("%size%", imageWidth.toString()),
        contentDescription = PresentationText.Resource(R.string.unit_photo).asString(),
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth
      )

      Column(
        modifier = Modifier.padding(SpaceNormal).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(text = unit.name)
      }
    }
  }
}

@Preview(
  name =  "Unit Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun UnitItemPreview() {
  UnitItem(
    unit = LaximoUnit(
      id = "id",
      name = "name",
      imageUrl = "imageUrl",
      code = "code",
      ssd = "ssd"
    ),
    onClick = {}
  )
}