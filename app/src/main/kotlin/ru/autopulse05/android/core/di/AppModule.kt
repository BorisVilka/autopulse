package ru.autopulse05.android.core.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.autopulse05.android.core.data.source.AppDatabase
import ru.autopulse05.android.shared.data.remote.HttpRoutes
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Singleton
  @Provides
  fun provideAppDatabase(
    application: Application
  ): AppDatabase = Room
    .databaseBuilder(
      application,
      AppDatabase::class.java,
      AppDatabase.NAME
    )
    .build()

  @Singleton
  @Provides
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
    .apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

  @Singleton
  @Provides
  fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

  @Singleton
  @Provides
  fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

  @Singleton
  @Provides
  fun provideRetrofit(
    converterFactory: GsonConverterFactory,
    client: OkHttpClient
  ): Retrofit = Retrofit.Builder()
    .baseUrl(HttpRoutes.ABCP_BASE_URL)
    .addConverterFactory(converterFactory)
    .client(client)
    .build()
}