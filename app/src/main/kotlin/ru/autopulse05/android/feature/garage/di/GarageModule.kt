package ru.autopulse05.android.feature.garage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.core.data.source.AppDatabase
import ru.autopulse05.android.feature.garage.data.remote.GarageRemoteService
import ru.autopulse05.android.feature.garage.data.repository.GarageRepositoryImpl
import ru.autopulse05.android.feature.garage.domain.repository.GarageRepository
import ru.autopulse05.android.feature.garage.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GarageModule {


  @Singleton
  @Provides
  fun provideGarageRemoteService(
    retrofit: Retrofit
  ): GarageRemoteService = retrofit.create(
    GarageRemoteService::class.java
  )

  @Singleton
  @Provides
  fun provideGarageAddCarUseCase(
    remoteService: GarageRemoteService
  ): GarageAddCarUseCase = GarageAddCarUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideGarageDeleteCarUseCase(
     remoteService: GarageRemoteService
  ): GarageDeleteCarUseCase = GarageDeleteCarUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideGarageGetCarUseCase(
    remoteService: GarageRemoteService
  ): GarageGetCarUseCase = GarageGetCarUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideGarageUpdateCarUseCase(
    remoteService: GarageRemoteService
  ): GarageUpdateCarUseCase = GarageUpdateCarUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideGarageUpdateUseCase(
    remoteService: GarageRemoteService
  ): GarageUpdateUseCase = GarageUpdateUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideGarageUseCases(
    addCar: GarageAddCarUseCase,
    deleteCar: GarageDeleteCarUseCase,
    getCar: GarageGetCarUseCase,
    updateCar: GarageUpdateCarUseCase,
    update: GarageUpdateUseCase
  ) = GarageUseCases(
    addCar = addCar,
    deleteCar = deleteCar,
    getCar = getCar,
    updateCar = updateCar,
    update = update
  )
}