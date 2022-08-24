package ru.autopulse05.android.feature.garage.data.remote

object GarageHttpRoutes {
  const val GARAGE = "user/garage"
  const val CAR = "$GARAGE/catalog"
  const val ADD = "$GARAGE/addCar"
  const val UPDATE = "$GARAGE/update"
  const val DELETE = "$GARAGE/delete"
}