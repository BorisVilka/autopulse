package ru.autopulse05.android.feature.vin.data.remote.dto

import ru.autopulse05.android.feature.vin.domain.model.VinInfo


data class VinInfoDto(
  val _id: String,
  val resellerId: String,
  val siteId: String,
  val clientId: String,
  val guestInfo: GuestInfoDto
)

fun VinInfoDto.toVinInfo() = VinInfo(
  id = _id,
  resellerId = resellerId,
  siteId = siteId,
  clientId = clientId,
  guestInfo = guestInfo.toGuestInfo(),
)