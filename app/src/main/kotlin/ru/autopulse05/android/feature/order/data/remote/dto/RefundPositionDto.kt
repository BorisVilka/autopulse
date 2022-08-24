package ru.autopulse05.android.feature.order.data.remote.dto

import ru.autopulse05.android.feature.order.domain.model.RefundPosition


data class RefundPositionDto(
  val orderPickingPositionId: String,
  val quantity: Int,
  val type: Int,
  val comment: String,
)


fun RefundPositionDto.toRefundPosition() = RefundPosition(
  orderPickingPositionId = orderPickingPositionId,
  quantity = quantity,
  type = type,
  comment = comment,
)



