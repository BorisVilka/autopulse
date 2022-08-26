package ru.autopulse05.android.feature.car.presentation.edit.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun GarageCar(
  car: Car,
  modifier: Modifier = Modifier,
  onClick: (Car) -> Unit = {},
  originalClick: (String) -> Unit = {},
  requestVinClick: () -> Unit = {}
) {
  Column(
    modifier = modifier
      .clickable { onClick(car) }
      .shadow(elevation = SpaceSmall)
      .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
      .padding(SpaceNormal)
      .fillMaxWidth()
  ) {
    Text(
      text = car.name.orEmpty(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    Text(text = car.model.name.orEmpty())

    Spacer(modifier = Modifier.height(SpaceNormal))

    Button(onClick = {
          originalClick(car.vin.orEmpty())
       },
      contentPadding = PaddingValues(vertical = SpaceSmall, horizontal = SpaceSmall),
      colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface)
    ) {
      Text("Оригинальные каталоги")
    }

    Spacer(modifier = Modifier.height(SpaceNormal))

    Button(onClick = {
          requestVinClick()
        },
      contentPadding = PaddingValues(vertical = SpaceSmall, horizontal = SpaceSmall),
      colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface)
    ) {
      Text("Подбор запчасти")
    }
  }
}

@Preview(
  name = "Garage Car",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun GarageCarPreview() {
  GarageCar(
    car = Car(
      id = "id",
      name = "name",
      comment = "comment",
      year = 2000,
      vin = "vin",
      frame = "frame",
      mileage = "mileage",
      manufacturerId = 1,
      manufacturer = "manufacturer",
      modelId = 1,
      modelName = "modelName",
      modificationName = "modification",
      modificationId = 1,
      vehicleRegPlate = "vehicleRegPlate",
    )
  )
}