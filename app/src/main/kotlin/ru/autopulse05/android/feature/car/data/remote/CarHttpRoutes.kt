package ru.autopulse05.android.feature.car.data.remote

object CarHttpRoutes {
  // CarTree
  const val MARKS = "cartree/manufacturers"
  const val MODELS = "cartree/models"
  const val YEARS = "cartree/years"
  const val MODIFICATIONS = "cartree/modifications"

  // Garage
  const val GARAGE = "user/garage"
  const val GARAGE_CAR = "$GARAGE/catalog"
  const val GARAGE_ADD = "$GARAGE/addCar"
  const val GARAGE_UPDATE = "$GARAGE/update"
  const val GARAGE_DELETE = "$GARAGE/delete"
}