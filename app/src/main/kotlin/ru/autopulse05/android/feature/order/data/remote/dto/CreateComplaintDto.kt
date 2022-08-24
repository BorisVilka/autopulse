package ru.autopulse05.android.feature.order.data.remote.dto

import ru.autopulse05.android.feature.order.domain.model.CreateComplaint


data class CreateComplaintDto(
  val id: String,
  val number: String,
  val createDate: String,
  val creatorId: String,
  val expertId: String,
  val clientId: String,
  val agreementId: String,
  val orderPickingId: String,
  val positionCount: String,
  val canceledPositionCount: String,
  val positions: List<CreateComplaintPositionDto>,
)

fun CreateComplaintDto.toCreateComplaint() = CreateComplaint(
  id = id,
  number = number,
  createDate = createDate,
  creatorId = creatorId,
  expertId = expertId,
  clientId = clientId,
  agreementId = agreementId,
  orderPickingId = orderPickingId,
  positionCount = positionCount,
  canceledPositionCount = canceledPositionCount,
  positions = positions.map { it.toCreateComplaintPosition() },
)



