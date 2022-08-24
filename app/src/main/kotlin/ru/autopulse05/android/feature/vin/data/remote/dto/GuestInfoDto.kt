package ru.autopulse05.android.feature.vin.data.remote.dto

import ru.autopulse05.android.feature.vin.domain.model.GuestInfo

data class GuestInfoDto(
  val name: String,
  val phone: String,
  val email: String,
)

fun GuestInfoDto.toGuestInfo() = GuestInfo(
  name = name,
  phone = phone,
  email = email,
)