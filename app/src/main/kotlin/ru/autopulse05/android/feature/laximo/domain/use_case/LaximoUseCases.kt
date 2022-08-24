package ru.autopulse05.android.feature.laximo.domain.use_case

data class LaximoUseCases(
  val getCatalogs: LaximoGetCatalogsUseCase,
  val getVehicles: LaximoGetVehiclesUseCase,
  val getVehicleFormFields: LaximoGetVehicleFormFieldsUseCase,
  val getVehiclesByVin: LaximoGetVehiclesByVinUseCase,
  val getVehiclesByFrame: LaximoGetVehiclesByFrameUseCase,
  val getCategories: LaximoGetCategoriesUseCase,
  val getUnits: LaximoGetUnitsUseCase,
  val getUnit: LaximoGetUnitUseCase,
  val getDetails: LaximoGetDetailsUseCase
)
