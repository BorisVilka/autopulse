package ru.autopulse05.android.feature.order.data.remote.dto

import ru.autopulse05.android.feature.order.domain.model.ComplaintPosition


data class ComplaintPositionDto(
  val orderPickingPositionId: String,
  val quantity: Int,
  val type: Int,
  val comment: String,
)


fun ComplaintPositionDto.toComplaintPosition() = ComplaintPosition(
  orderPickingPositionId = orderPickingPositionId,
  quantity = quantity,
  type = type,
  comment = comment,
)



