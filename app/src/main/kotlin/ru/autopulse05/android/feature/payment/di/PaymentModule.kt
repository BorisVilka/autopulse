package ru.autopulse05.android.feature.payment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.feature.payment.data.remote.PaymentRemoteService
import ru.autopulse05.android.feature.payment.domain.use_case.PaymentGetMethodsUseCase
import ru.autopulse05.android.feature.payment.domain.use_case.PaymentUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentModule {

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
  fun providePaymentUseCases(
    getMethods: PaymentGetMethodsUseCase,
  ) = PaymentUseCases(
    getMethods = getMethods
  )
}