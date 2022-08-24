package ru.autopulse05.android.feature.order.domain.use_case

data class OrderUseCases(
  val cancel: OrderCancelUseCase,
  val instant: OrderInstantUseCase,
  val getAll: OrderGetAllUseCase,
  val create: OrderCreateUseCase,
  val order: OrderUseCase,

  // Refunds
  val getRefunds: OrderGetRefundsUseCase,
  val createRefund: OrderCreateRefundUseCase,

  // Pickings
  val getPickings: OrderGetPickingsUseCase,

  // Validation
  val validateComment: OrderValidateCommentUseCase,

  val payment: OrderGetPaymentUseCase
)
