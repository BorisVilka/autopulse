package ru.autopulse05.android.feature.user.domain.use_case

data class UserUseCases(
  // Main
  val getInfo: UserUpdateInfoUseCase,
  val sendRestoreCode: UserSendRestoreCodeUseCase,
  val restorePassword: UserRestorePasswordUseCase,
  val signUp: UserSignUpUseCase,
  val signIn: UserSignInUseCase,
  val signOut: UserSignOutUseCase,
  val update: UserUpdateUseCase,

  // Validation
  val validatePassword: UserValidatePasswordUseCase,
  val validateRepeatedPassword: UserValidateRepeatedPasswordUseCase,
  val validateCity: UserValidateCityUseCase,
  val validateSurname: UserValidateSurnameUseCase,
  val validateSecondName: UserValidateSecondNameUseCase,
  val getPayments: UserGetPaymentsUseCase
)