package ru.autopulse05.android.feature.basket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.core.data.source.AppDatabase
import ru.autopulse05.android.feature.basket.data.remote.BasketRemoteService
import ru.autopulse05.android.feature.basket.data.repository.BasketRepositoryImpl
import ru.autopulse05.android.feature.basket.domain.repository.BasketRepository
import ru.autopulse05.android.feature.basket.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BasketModule {

  @Singleton
  @Provides
  fun provideBasketRemoteService(
    retrofit: Retrofit
  ): BasketRemoteService = retrofit.create(BasketRemoteService::class.java)

  @Singleton
  @Provides
  fun provideBasketAddUseCase(
     remoteService: BasketRemoteService
  ) = BasketAddUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideBasketClearUseCase(
    remoteService: BasketRemoteService
  ) = BasketClearUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideBasketGetContentUseCase(
    remoteService: BasketRemoteService
  ) = BasketUpdateUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideBasketGetOptionsUseCase(
    remoteService: BasketRemoteService
  ) = BasketGetOptionsUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideBasketUseCases(
    add: BasketAddUseCase,
    clear: BasketClearUseCase,
    getContent: BasketUpdateUseCase,
    getOptions: BasketGetOptionsUseCase
  ) = BasketUseCases(
    add = add,
    clear = clear,
    update = getContent,
    getOptions = getOptions
  )
}