package ru.autopulse05.android.feature.order.domain.model

import ru.autopulse05.android.feature.order.data.remote.dto.ComplaintPositionDto

data class ComplaintPosition(
  val orderPickingPositionId: String,
  val quantity: Int,
  val type: Int,
  val comment: String,
)

fun ComplaintPosition.toComplaintPositionDto() = ComplaintPositionDto(
  orderPickingPositionId = orderPickingPositionId,
  quantity = quantity,
  type = type,
  comment = comment,
)