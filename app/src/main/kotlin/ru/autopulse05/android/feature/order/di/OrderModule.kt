package ru.autopulse05.android.feature.order.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderModule {

  @Singleton
  @Provides
  fun provideOrderRemoteService(
    retrofit: Retrofit
  ): OrderRemoteService = retrofit.create(
    OrderRemoteService::class.java
  )

  @Provides
  @Singleton
  fun provideOrderCancelUseCase(
    remoteService: OrderRemoteService
  ): OrderCancelUseCase = OrderCancelUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderInstantUseCase(
    remoteService: OrderRemoteService
  ): OrderInstantUseCase = OrderInstantUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderGetAllUseCase(
    remoteService: OrderRemoteService
  ): OrderGetAllUseCase = OrderGetAllUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderGetComplaintsUseCase(
    remoteService: OrderRemoteService
  ): OrderGetRefundsUseCase = OrderGetRefundsUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderCreateComplaintUseCase(
    remoteService: OrderRemoteService
  ): OrderCreateRefundUseCase = OrderCreateRefundUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderGetPickingsUseCase(
    remoteService: OrderRemoteService
  ): OrderGetPickingsUseCase = OrderGetPickingsUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderCreateUseCase(
    remoteService: OrderRemoteService
  ): OrderCreateUseCase = OrderCreateUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderUseCase(
    remoteService: OrderRemoteService
  ): OrderUseCase = OrderUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderGetPaymentCase(
    remoteService: OrderRemoteService
  ): OrderGetPaymentUseCase = OrderGetPaymentUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderGetOfficesCase(
    remoteService: OrderRemoteService
  ): OrderGetOfficesUseCase = OrderGetOfficesUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderGetAddressCase(
    remoteService: OrderRemoteService
  ): OrderGetShipmentAddressUseCase = OrderGetShipmentAddressUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideOrderValidateCommentUseCase(): OrderValidateCommentUseCase =
    OrderValidateCommentUseCase()

  @Singleton
  @Provides
  fun provideOrderUseCases(
    cancel: OrderCancelUseCase,
    instant: OrderInstantUseCase,
    getAll: OrderGetAllUseCase,
    getComplaints: OrderGetRefundsUseCase,
    createComplaint: OrderCreateRefundUseCase,
    getPickings: OrderGetPickingsUseCase,
    create: OrderCreateUseCase,
    order: OrderUseCase,
    validateComment: OrderValidateCommentUseCase,
    payment: OrderGetPaymentUseCase,
    offices: OrderGetOfficesUseCase,
    addressUseCase: OrderGetShipmentAddressUseCase
  ) = OrderUseCases(
    cancel = cancel,
    instant = instant,
    getAll = getAll,
    create = create,
    order = order,
    getRefunds = getComplaints,
    createRefund = createComplaint,
    getPickings = getPickings,
    validateComment = validateComment,
    payment = payment,
    offices = offices,
    address = addressUseCase
  )
}