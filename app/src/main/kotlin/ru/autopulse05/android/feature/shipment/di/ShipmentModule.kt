package ru.autopulse05.android.feature.shipment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.core.data.source.AppDatabase
import ru.autopulse05.android.feature.shipment.data.remote.ShipmentRemoteService
import ru.autopulse05.android.feature.shipment.data.repository.ShipmentAddressesRepositoryImpl
import ru.autopulse05.android.feature.shipment.domain.repository.ShipmentAddressesRepository
import ru.autopulse05.android.feature.shipment.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShipmentModule {

  @Singleton
  @Provides
  fun provideShipmentAddressesRepository(
    db: AppDatabase,
  ): ShipmentAddressesRepository = ShipmentAddressesRepositoryImpl(dao = db.shipmentAddressesDao)

  @Singleton
  @Provides
  fun provideShipmentRemoteService(
    retrofit: Retrofit
  ): ShipmentRemoteService = retrofit.create(ShipmentRemoteService::class.java)

  @Singleton
  @Provides
  fun provideShipmentGetMethodsUseCase(
    remoteService: ShipmentRemoteService
  ): ShipmentGetMethodsUseCase = ShipmentGetMethodsUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideShipmentGetOfficesUseCase(
    remoteService: ShipmentRemoteService
  ): ShipmentGetOfficesUseCase = ShipmentGetOfficesUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideShipmentUpdateAllAddressesUseCase(
    repository: ShipmentAddressesRepository,
    remoteService: ShipmentRemoteService
  ): ShipmentUpdateAddressesUseCase = ShipmentUpdateAddressesUseCase(
    repository = repository,
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideShipmentGetDatesUseCase(
    remoteService: ShipmentRemoteService
  ): ShipmentGetDatesUseCase = ShipmentGetDatesUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideShipmentAddAddressUseCase(
    repository: ShipmentAddressesRepository,
    remoteService: ShipmentRemoteService
  ): ShipmentAddAddressUseCase = ShipmentAddAddressUseCase(
    repository = repository,
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun providePaymentAndShipmentUseCases(
    getShipmentMethods: ShipmentGetMethodsUseCase,
    getShipmentOffices: ShipmentGetOfficesUseCase,
    getShipmentAddresses: ShipmentUpdateAddressesUseCase,
    getShipmentDates: ShipmentGetDatesUseCase,
    addShipmentAddress: ShipmentAddAddressUseCase
  ) = ShipmentUseCases(
    getShipmentMethods = getShipmentMethods,
    getShipmentOffices = getShipmentOffices,
    getShipmentAddresses = getShipmentAddresses,
    getShipmentDates = getShipmentDates,
    addShipmentAddress = addShipmentAddress
  )
}