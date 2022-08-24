package ru.autopulse05.android.feature.search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.feature.search.data.remote.SearchRemoteService
import ru.autopulse05.android.feature.search.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

  @Singleton
  @Provides
  fun provideSearchRemoteService(
    retrofit: Retrofit
  ): SearchRemoteService = retrofit.create(
    SearchRemoteService::class.java
  )

  @Provides
  @Singleton
  fun provideSearchGetBrandsUseCase(
    remoteService: SearchRemoteService
  ) = SearchGetBrandsUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideSearchGetArticlesUseCase(
    remoteService: SearchRemoteService
  ) = SearchGetArticlesUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideSearchBatchUseCase(
    remoteService: SearchRemoteService
  ) = SearchBatchUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideSearchGetHistoryUseCase(
    remoteService: SearchRemoteService
  ) = SearchGetHistoryUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideSearchGetTipsUseCase(
    remoteService: SearchRemoteService
  ) = SearchGetTipsUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideSearchValidateNumberUseCase() = SearchValidateNumberUseCase()

  @Singleton
  @Provides
  fun provideSearchUseCases(
    getBrands: SearchGetBrandsUseCase,
    getArticles: SearchGetArticlesUseCase,
    batch: SearchBatchUseCase,
    getHistory: SearchGetHistoryUseCase,
    getTips: SearchGetTipsUseCase,
    validateNumber: SearchValidateNumberUseCase
  ) = SearchUseCases(
    getBrands = getBrands,
    getArticle = getArticles,
    batch = batch,
    getHistory = getHistory,
    getTips = getTips,
    validateNumber = validateNumber
  )
}