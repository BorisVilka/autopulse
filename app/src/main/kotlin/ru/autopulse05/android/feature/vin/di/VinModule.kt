package ru.autopulse05.android.feature.vin.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.autopulse05.android.feature.vin.data.remote.VinHttpRoutes
import ru.autopulse05.android.feature.vin.data.remote.VinRemoteService
import ru.autopulse05.android.feature.vin.domain.use_case.VinAddUseCase
import ru.autopulse05.android.feature.vin.domain.use_case.VinUseCases
import ru.autopulse05.android.feature.vin.domain.use_case.VinValidatePartsUseCase
import ru.autopulse05.android.feature.vin.domain.use_case.VinValidateUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VinModule {

  @Singleton
  @Provides
  fun provideVinRemoteService(
    converterFactory: GsonConverterFactory,
    client: OkHttpClient
  ): VinRemoteService = Retrofit.Builder()
    .baseUrl(VinHttpRoutes.VINQUERY_BASE_URL)
    .addConverterFactory(converterFactory)
    .client(client)
    .build()
    .create(VinRemoteService::class.java)

  @Provides
  @Singleton
  fun provideVinAddUseCase(
    remoteService: VinRemoteService
  ): VinAddUseCase = VinAddUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideVinValidatePartsUseCase(): VinValidatePartsUseCase = VinValidatePartsUseCase()

  @Provides
  @Singleton
  fun provideVinValidateUseCase(): VinValidateUseCase = VinValidateUseCase()

  @Singleton
  @Provides
  fun provideVinUseCases(
    add: VinAddUseCase,
    validateParts: VinValidatePartsUseCase,
    validate: VinValidateUseCase
  ): VinUseCases = VinUseCases(
    add = add,
    validateParts = validateParts,
    validate = validate
  )
}