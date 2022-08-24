package ru.autopulse05.android.feature.basket.domain.use_case

data class BasketUseCases(
  val add: BasketAddUseCase,
  val clear: BasketClearUseCase,
  val update: BasketUpdateUseCase,
  val getOptions: BasketGetOptionsUseCase
)
