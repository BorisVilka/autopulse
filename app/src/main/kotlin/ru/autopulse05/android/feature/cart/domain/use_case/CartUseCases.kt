package ru.autopulse05.android.feature.cart.domain.use_case

data class CartUseCases(
  val add: CartAddUseCase,
  val clear: CartClearUseCase,
  val update: CartUpdateUseCase
)
