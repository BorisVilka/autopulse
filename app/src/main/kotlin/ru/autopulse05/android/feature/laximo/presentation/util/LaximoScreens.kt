package ru.autopulse05.android.feature.laximo.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class LaximoScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "laximo"
  }

  object Catalogs : LaximoScreens("catalogs")
  object Auto: LaximoScreens("auto")
  object VehicleSearch : LaximoScreens("vehicle_search")
  object Vehicles : LaximoScreens("vehicles")
  object Units : LaximoScreens("units")
  object Categories : LaximoScreens("categories")
  object Unit : LaximoScreens("unit")
}