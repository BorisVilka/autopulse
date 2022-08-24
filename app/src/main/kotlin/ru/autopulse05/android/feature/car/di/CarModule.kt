package ru.autopulse05.android.feature.car.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.core.data.source.AppDatabase
import ru.autopulse05.android.feature.car.data.remote.CarRemoteService
import ru.autopulse05.android.feature.car.data.repository.CarRepositoryImpl
import ru.autopulse05.android.feature.car.domain.repository.CarRepository
import ru.autopulse05.android.feature.car.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CarModule {

  @Singleton
  @Provides
  fun provideCarRemoteService(
    retrofit: Retrofit
  ): CarRemoteService = retrofit.create(
    CarRemoteService::class.java
  )

  @Provides
  @Singleton
  fun provideCarRepository(
    db: AppDatabase
  ): CarRepository = CarRepositoryImpl(dao = db.carDao)

  @Singleton
  @Provides
  fun provideCarGetMarksUseCase(
    remoteService: CarRemoteService
  ): CarGetMarksUseCase = CarGetMarksUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCarGetModelsUseCase(
    remoteService: CarRemoteService
  ): CarGetModelsUseCase = CarGetModelsUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCarGetModificationsUseCase(
    remoteService: CarRemoteService
  ): CarGetModificationsUseCase = CarGetModificationsUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCarGetYearsUseCase(
    remoteService: CarRemoteService
  ): CarGetYearsUseCase = CarGetYearsUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCarAddToGarageCarUseCase(
    repository: CarRepository,
    remoteService: CarRemoteService
  ): CarAddToGarageCarUseCase = CarAddToGarageCarUseCase(
    repository = repository,
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCarDeleteFromGarageUseCase(
    repository: CarRepository,
    remoteService: CarRemoteService
  ): CarDeleteFromGarageUseCase = CarDeleteFromGarageUseCase(
    repository = repository,
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCarGetFromGarageUseCase(
    remoteService: CarRemoteService
  ): CarGetFromGarageUseCase = CarGetFromGarageUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCarUpdateInGarageUseCase(
    repository: CarRepository,
    remoteService: CarRemoteService
  ): CarUpdateInGarageUseCase = CarUpdateInGarageUseCase(
    repository = repository,
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCarUpdateGarageUseCase(
    repository: CarRepository,
    remoteService: CarRemoteService
  ): CarUpdateGarageUseCase = CarUpdateGarageUseCase(
    repository = repository,
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideValidateFrameNameUseCase(
  ): CarValidateFrameNameUseCase = CarValidateFrameNameUseCase()

  @Singleton
  @Provides
  fun provideValidateFrameNumberUseCase(
  ): CarValidateFrameNumberUseCase = CarValidateFrameNumberUseCase()

  @Singleton
  @Provides
  fun provideCarUseCases(
    getMarks: CarGetMarksUseCase,
    getModels: CarGetModelsUseCase,
    getModifications: CarGetModificationsUseCase,
    getYears: CarGetYearsUseCase,
    addToGarage: CarAddToGarageCarUseCase,
    deleteFromGarage: CarDeleteFromGarageUseCase,
    getCarFromGarage: CarGetFromGarageUseCase,
    updateInGarage: CarUpdateInGarageUseCase,
    updateGarage: CarUpdateGarageUseCase,
    validateFrameNumber: CarValidateFrameNumberUseCase,
    validateFrameName: CarValidateFrameNameUseCase
  ) = CarUseCases(
    getMarks = getMarks,
    getModels = getModels,
    getModifications = getModifications,
    getYears = getYears,
    addToGarage = addToGarage,
    deleteFromGarage = deleteFromGarage,
    getCarFromGarage = getCarFromGarage,
    updateInGarage = updateInGarage,
    updateGarage = updateGarage,
    validateFrameNumber = validateFrameNumber,
    validateFrameName = validateFrameName
  )
}