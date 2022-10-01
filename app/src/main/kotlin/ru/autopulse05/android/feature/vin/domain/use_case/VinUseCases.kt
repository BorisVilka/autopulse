package ru.autopulse05.android.feature.vin.domain.use_case

data class VinUseCases(
  val add: VinAddUseCase,
  val validateParts: VinValidatePartsUseCase,
  val validate: VinValidateUseCase,
  val list: VinListUseCase,
  val chat: VinGetChatUseCase,
  val message: VinNewMessageUseCase,
  val update: VinUpdateUseCase
)
