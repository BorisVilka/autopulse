package ru.autopulse05.android.feature.payment_and_shipment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.core.data.source.AppDatabase
import ru.autopulse05.android.feature.payment_and_shipment.data.remote.PaymentRemoteService
import ru.autopulse05.android.feature.payment_and_shipment.data.remote.ShipmentRemoteService
import ru.autopulse05.android.feature.payment_and_shipment.data.repository.ShipmentAddressesRepositoryImpl
import ru.autopulse05.android.feature.payment_and_shipment.domain.repository.ShipmentAddressesRepository
import ru.autopulse05.android.feature.payment_and_shipment.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentAndShipmentModule {


  @Singleton
  @Provides
  fun provideShipmentRemoteService(
    retrofit: Retrofit
  ): ShipmentRemoteService = retrofit.create(ShipmentRemoteService::class.java)

  @Singleton
  @Provides
  fun providePaymentRemoteService(
    retrofit: Retrofit
  ): PaymentRemoteService = retrofit.create(PaymentRemoteService::class.java)


  @Singleton
  @Provides
  fun providePaymentGetMethodsUseCase(
    remoteService: PaymentRemoteService
  ): PaymentGetMethodsUseCase = PaymentGetMethodsUseCase(
    remoteService = remoteService
  )

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
    getPaymentMethods: PaymentGetMethodsUseCase,
    getShipmentMethods: ShipmentGetMethodsUseCase,
    getShipmentOffices: ShipmentGetOfficesUseCase,
    getShipmentAddresses: ShipmentUpdateAddressesUseCase,
    getShipmentDates: ShipmentGetDatesUseCase,
    addShipmentAddress: ShipmentAddAddressUseCase
  ) = PaymentAndShipmentUseCases(
    getPaymentMethods = getPaymentMethods,
    getShipmentMethods = getShipmentMethods,
    getShipmentOffices = getShipmentOffices,
    getShipmentAddresses = getShipmentAddresses,
    getShipmentDates = getShipmentDates,
    addShipmentAddress = addShipmentAddress
  )
}