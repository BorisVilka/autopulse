package ru.autopulse05.android.shared.domain.use_case

data class SharedUseCases(
  val dialTo: DialToUseCase,
  val mailTo: MailToUseCase,

  // Validation
  val validateEmail: ValidateEmailUseCase,
  val validateName: ValidateNameUseCase,
  val validatePhone: ValidatePhoneUseCase,
  val validateAddress: ValidateAddressUseCase
)