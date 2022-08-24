package ru.autopulse05.android.feature.order.data.remote.dto

import ru.autopulse05.android.feature.order.domain.model.TsOrder


data class TsOrderDto(
  val id: String,
  val number: String,
  val agreementId: String,
  val managerId: String,
  val createTime: String,
  val updateTime: String
)

fun TsOrderDto.toTsOrder() = TsOrder(
  id = id,
  number = number,
  agreementId = agreementId,
  managerId = managerId,
  createTime = createTime,
  updateTime = updateTime
)

