package ru.autopulse05.android.feature.user.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.autopulse05.android.core.data.source.AppDatabase
import ru.autopulse05.android.feature.user.data.remote.UserRemoteService
import ru.autopulse05.android.feature.user.data.repository.UserRepositoryImpl
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.feature.user.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

  @Provides
  @Singleton
  fun provideUserRepository(
    db: AppDatabase
  ): UserRepository = UserRepositoryImpl(dao = db.userDao)

  @Singleton
  @Provides
  fun provideUserRemoteService(
    retrofit: Retrofit
  ): UserRemoteService = retrofit.create(
    UserRemoteService::class.java
  )

  @Singleton
  @Provides
  fun provideUserSignUpUseCase(
    repository: UserRepository,
    remoteService: UserRemoteService,
    application: Application
  ) = UserSignUpUseCase(
    remoteService = remoteService,
    repository = repository,
    application = application
  )

  @Singleton
  @Provides
  fun provideUserUpdateInfoUseCase(
    repository: UserRepository,
    remoteService: UserRemoteService
  ) = UserUpdateInfoUseCase(
    repository = repository,
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideUserSendRestoreCodeUseCase(
    remoteService: UserRemoteService
  ) = UserSendRestoreCodeUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideUserValidatePasswordUseCase(
    application: Application
  ): UserValidatePasswordUseCase = UserValidatePasswordUseCase(
    application = application
  )

  @Singleton
  @Provides
  fun provideUserValidateRepeatedPasswordUseCase(
    application: Application
  ): UserValidateRepeatedPasswordUseCase = UserValidateRepeatedPasswordUseCase(
    application = application
  )

  @Singleton
  @Provides
  fun provideUserValidateCityUseCase(
    application: Application
  ): UserValidateCityUseCase = UserValidateCityUseCase(
    application = application
  )

  @Singleton
  @Provides
  fun provideUserRestorePasswordUseCase(
    remoteService: UserRemoteService,
  ): UserRestorePasswordUseCase = UserRestorePasswordUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideUserSignInUseCase(
    application: Application,
    remoteService: UserRemoteService,
    repository: UserRepository
  ): UserSignInUseCase = UserSignInUseCase(
    application = application,
    remoteService = remoteService,
    repository = repository
  )

  @Singleton
  @Provides
  fun provideUserSignOutUseCase(
    application: Application,
    database: AppDatabase,
  ): UserSignOutUseCase = UserSignOutUseCase(
    application = application,
    database = database
  )

  @Singleton
  @Provides
  fun provideUserValidateSurnameUseCase(): UserValidateSurnameUseCase = UserValidateSurnameUseCase()

  @Singleton
  @Provides
  fun provideUserValidateSecondNameUseCase(): UserValidateSecondNameUseCase = UserValidateSecondNameUseCase()

  @Singleton
  @Provides
  fun provideUserUpdateUseCase(
    remoteService: UserRemoteService,
    repository: UserRepository
  ): UserUpdateUseCase = UserUpdateUseCase(
    remoteService = remoteService,
    repository = repository
  )

  @Singleton
  @Provides
  fun provideUserPaymentUseCase(
    remoteService: UserRemoteService
  ) = UserGetPaymentsUseCase(
    remoteService = remoteService
  )

  @Singleton
  @Provides
  fun provideUserUseCases(
    getInfo: UserUpdateInfoUseCase,
    sendRestoreCode: UserSendRestoreCodeUseCase,
    restorePassword: UserRestorePasswordUseCase,
    signUp: UserSignUpUseCase,
    signIn: UserSignInUseCase,
    signOut: UserSignOutUseCase,
    update: UserUpdateUseCase,
    validatePassword: UserValidatePasswordUseCase,
    validateRepeatedPassword: UserValidateRepeatedPasswordUseCase,
    validateCity: UserValidateCityUseCase,
    validateSurname: UserValidateSurnameUseCase,
    validateSecondName: UserValidateSecondNameUseCase,
    getPaymentsUseCase: UserGetPaymentsUseCase
  ) = UserUseCases(
    getInfo = getInfo,
    sendRestoreCode = sendRestoreCode,
    restorePassword = restorePassword,
    signUp = signUp,
    signIn = signIn,
    signOut = signOut,
    update = update,
    validatePassword = validatePassword,
    validateRepeatedPassword = validateRepeatedPassword,
    validateCity = validateCity,
    validateSurname = validateSurname,
    validateSecondName = validateSecondName,
    getPayments = getPaymentsUseCase
  )
}