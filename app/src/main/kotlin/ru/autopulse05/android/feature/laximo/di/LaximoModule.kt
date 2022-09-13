package ru.autopulse05.android.feature.laximo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteServiceImpl
import ru.autopulse05.android.feature.laximo.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LaximoModule {

  @Singleton
  @Provides
  fun provideLaximoRemoteService(): LaximoRemoteService = LaximoRemoteServiceImpl()


  @Singleton
  @Provides
  fun provideLaximoGetCatalogsUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetCatalogsUseCase = LaximoGetCatalogsUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideLaximoGetVehiclesUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetVehiclesUseCase = LaximoGetVehiclesUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideLaximoGetVehiclesByVinUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetVehiclesByVinUseCase = LaximoGetVehiclesByVinUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideLaximoGetVehiclesByFrameUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetVehiclesByFrameUseCase = LaximoGetVehiclesByFrameUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideLaximoGetCategoriesUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetCategoriesUseCase = LaximoGetCategoriesUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideLaximoGetUnitsUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetUnitsUseCase = LaximoGetUnitsUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideLaximoGetUnitUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetUnitUseCase = LaximoGetUnitUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideLaximoGetVehicleFormFieldsUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetVehicleFormFieldsUseCase = LaximoGetVehicleFormFieldsUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideLaximoGetDetailsUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetDetailsUseCase = LaximoGetDetailsUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideLaximoGetImageUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetImagesUseCase = LaximoGetImagesUseCase(
    remoteService = remoteService
  )
  @Singleton
  @Provides
  fun provideLaximoGetApplicationUseCase(
    remoteService: LaximoRemoteService
  ): LaximoGetApplicationUseCase = LaximoGetApplicationUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideLaximoUseCases(
    getCatalogs: LaximoGetCatalogsUseCase,
    getVehicles: LaximoGetVehiclesUseCase,
    getVehiclesByVin: LaximoGetVehiclesByVinUseCase,
    getVehiclesByFrame: LaximoGetVehiclesByFrameUseCase,
    getCategories: LaximoGetCategoriesUseCase,
    getUnits: LaximoGetUnitsUseCase,
    getUnit: LaximoGetUnitUseCase,
    getVehicleFormFields: LaximoGetVehicleFormFieldsUseCase,
    getDetails: LaximoGetDetailsUseCase,
    getImagesUseCase: LaximoGetImagesUseCase,
    getApplicationUseCase: LaximoGetApplicationUseCase
  ) = LaximoUseCases(
    getCatalogs = getCatalogs,
    getVehicles = getVehicles,
    getVehiclesByVin = getVehiclesByVin,
    getVehiclesByFrame = getVehiclesByFrame,
    getCategories = getCategories,
    getUnits = getUnits,
    getUnit = getUnit,
    getVehicleFormFields = getVehicleFormFields,
    getDetails = getDetails,
    getImages = getImagesUseCase,
    getApplication = getApplicationUseCase
  )
}