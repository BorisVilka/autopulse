package ru.autopulse05.android.feature.order.domain.model

import ru.autopulse05.android.feature.order.data.remote.dto.RefundPositionDto

data class RefundPosition(
  val orderPickingPositionId: String,
  val quantity: Int,
  val type: Int,
  val comment: String,
)

fun RefundPosition.toRefundPositionDto() = RefundPositionDto(
  orderPickingPositionId = orderPickingPositionId,
  quantity = quantity,
  type = type,
  comment = comment,
)