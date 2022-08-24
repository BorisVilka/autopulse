package ru.autopulse05.android.feature.order.domain.model

data class TsOrder(
  val id: String,
  val number: String,
  val agreementId: String,
  val managerId: String,
  val createTime: String,
  val updateTime: String
)