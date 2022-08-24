package ru.autopulse05.android.shared.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.autopulse05.android.shared.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

  @Singleton
  @Provides
  fun provideCallToUseCase(
    application: Application
  ): DialToUseCase = DialToUseCase(
    application = application
  )

  @Singleton
  @Provides
  fun provideMailToUseCase(
    application: Application
  ): MailToUseCase = MailToUseCase(
    application = application
  )

  @Singleton
  @Provides
  fun provideValidateNameUseCase(
    application: Application
  ): ValidateNameUseCase = ValidateNameUseCase(
    application = application
  )

  @Singleton
  @Provides
  fun provideValidatePhoneUseCase(
    application: Application
  ): ValidatePhoneUseCase = ValidatePhoneUseCase(
    application = application
  )

  @Singleton
  @Provides
  fun provideValidateEmailUseCase(
    application: Application
  ): ValidateEmailUseCase = ValidateEmailUseCase(
    application = application
  )

  @Provides
  @Singleton
  fun provideValidateAddressUseCase(): ValidateAddressUseCase = ValidateAddressUseCase()

  @Singleton
  @Provides
  fun provideSharedUseCases(
    callTo: DialToUseCase,
    mailTo: MailToUseCase,
    validateName: ValidateNameUseCase,
    validatePhone: ValidatePhoneUseCase,
    validateEmail: ValidateEmailUseCase,
    validateAddress: ValidateAddressUseCase
  ): SharedUseCases = SharedUseCases(
    dialTo = callTo,
    mailTo = mailTo,
    validateName = validateName,
    validatePhone = validatePhone,
    validateEmail = validateEmail,
    validateAddress = validateAddress
  )
}