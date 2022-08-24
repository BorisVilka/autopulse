package ru.autopulse05.android.feature.product.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.feature.product.data.remote.ProductRemoteService
import ru.autopulse05.android.feature.product.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

  @Singleton
  @Provides
  fun provideProductRemoteService(
    retrofit: Retrofit
  ): ProductRemoteService = retrofit.create(
    ProductRemoteService::class.java
  )

  @Provides
  @Singleton
  fun provideProductGetAdvicesUseCase(
    remoteService: ProductRemoteService
  ) = ProductGetAdvicesUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideProductAdvicesBatchUseCase(
    remoteService: ProductRemoteService
  ) = ProductAdvicesBatchUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideProductGetArticlesBrandsUseCase(
    remoteService: ProductRemoteService
  ) = ProductGetBrandsUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideProductGetArticlesInfoUseCase(
    remoteService: ProductRemoteService
  ) = ProductGetInfoUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideProductUseCases(
    getAdvices: ProductGetAdvicesUseCase,
    advicesBatch: ProductAdvicesBatchUseCase,
    getArticlesBrands: ProductGetBrandsUseCase,
    getArticlesInfo: ProductGetInfoUseCase,
  ) = ProductUseCases(
    getAdvices = getAdvices,
    advicesBatch = advicesBatch,
    getArticlesBrands = getArticlesBrands,
    getArticlesInfo = getArticlesInfo
  )
}