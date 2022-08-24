package ru.autopulse05.android.feature.garage.presentation.components

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
import ru.autopulse05.android.feature.garage.domain.model.Car

@Composable
fun GarageCar(
  car: Car,
  modifier: Modifier = Modifier,
  onClick: (Car) -> Unit = {},
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
      text = car.name,
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    Text(text = car.model)
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
      model = "model",
      modificationId = 1,
      vehicleRegPlate = "vehicleRegPlate",
    )
  )
}