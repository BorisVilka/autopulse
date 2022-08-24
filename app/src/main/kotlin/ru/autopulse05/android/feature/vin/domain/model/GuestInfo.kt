package ru.autopulse05.android.feature.vin.domain.model

import ru.autopulse05.android.feature.vin.data.remote.dto.GuestInfoDto

data class GuestInfo(
  val name: String,
  val phone: String,
  val email: String,
)

fun GuestInfo.toGuestInfoDto() = GuestInfoDto(
  name = name,
  phone = phone,
  email = email,
)