package ru.autopulse05.android.feature.garage.domain.use_case

data class GarageUseCases(
  val addCar: GarageAddCarUseCase,
  val deleteCar: GarageDeleteCarUseCase,
  val getCar: GarageGetCarUseCase,
  val updateCar: GarageUpdateCarUseCase,
  val update: GarageUpdateUseCase
)