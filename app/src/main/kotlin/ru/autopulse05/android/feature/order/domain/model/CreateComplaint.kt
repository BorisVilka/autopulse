package ru.autopulse05.android.feature.order.domain.model


data class CreateComplaint(
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
  val positions: List<CreateComplaintPosition>,
)