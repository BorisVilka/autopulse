package ru.autopulse05.android.feature.car.domain.use_case

data class CarUseCases(
  val getMarks: CarGetMarksUseCase,
  val getModels: CarGetModelsUseCase,
  val getModifications: CarGetModificationsUseCase,
  val getYears: CarGetYearsUseCase,

  // Garage
  val addToGarage: CarAddToGarageCarUseCase,
  val deleteFromGarage: CarDeleteFromGarageUseCase,
  val getCarFromGarage: CarGetFromGarageUseCase,
  val updateInGarage: CarUpdateInGarageUseCase,
  val updateGarage: CarUpdateGarageUseCase,

  // Validation
  val validateFrameName: CarValidateFrameNameUseCase,
  val validateFrameNumber: CarValidateFrameNumberUseCase
)
