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
import ru.autopulse05.android.feature.vin.domain.use_case.*
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
  fun provideVinListUseCase(
    remoteService: VinRemoteService
  ): VinListUseCase = VinListUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideVinChatUseCase(
    remoteService: VinRemoteService
  ): VinGetChatUseCase = VinGetChatUseCase(
    remoteService = remoteService
  )

  @Provides
  @Singleton
  fun provideVinMessageUseCase(
    remoteService: VinRemoteService
  ): VinNewMessageUseCase = VinNewMessageUseCase(
    remoteService = remoteService
  )
  @Provides
  @Singleton
  fun provideVinUpdateUseCase(
    remoteService: VinRemoteService
  ): VinUpdateUseCase = VinUpdateUseCase(
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
    validate: VinValidateUseCase,
    list: VinListUseCase,
    chat: VinGetChatUseCase,
    message: VinNewMessageUseCase,
    updateUseCase: VinUpdateUseCase
  ): VinUseCases = VinUseCases(
    add = add,
    validateParts = validateParts,
    validate = validate,
    list = list,
    chat = chat,
    message = message,
    update = updateUseCase
  )
}