package ru.autopulse05.android.feature.cart.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.core.data.source.AppDatabase
import ru.autopulse05.android.feature.cart.data.remote.CartRemoteService
import ru.autopulse05.android.feature.cart.data.repository.CartRepositoryImpl
import ru.autopulse05.android.feature.cart.domain.repository.CartRepository
import ru.autopulse05.android.feature.cart.domain.use_case.CartAddUseCase
import ru.autopulse05.android.feature.cart.domain.use_case.CartClearUseCase
import ru.autopulse05.android.feature.cart.domain.use_case.CartUpdateUseCase
import ru.autopulse05.android.feature.cart.domain.use_case.CartUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

  @Singleton
  @Provides
  fun provideCartRepository(
    db: AppDatabase,
  ): CartRepository = CartRepositoryImpl(dao = db.cartDao)

  @Singleton
  @Provides
  fun provideCartRemoteService(
    retrofit: Retrofit
  ): CartRemoteService = retrofit.create(CartRemoteService::class.java)

  @Singleton
  @Provides
  fun provideCartAddUseCase(
    repository: CartRepository,
    remoteService: CartRemoteService
  ) = CartAddUseCase(
    repository = repository,
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCartClearUseCase(
    repository: CartRepository,
    remoteService: CartRemoteService
  ) = CartClearUseCase(
    repository = repository,
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCartGetContentUseCase(
    repository: CartRepository,
    remoteService: CartRemoteService
  ) = CartUpdateUseCase(
    repository = repository,
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideCartUseCases(
    add: CartAddUseCase,
    clear: CartClearUseCase,
    getContent: CartUpdateUseCase
  ) = CartUseCases(
    add = add,
    clear = clear,
    update = getContent
  )
}