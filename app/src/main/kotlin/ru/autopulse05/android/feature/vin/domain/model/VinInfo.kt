package ru.autopulse05.android.feature.vin.domain.model

data class VinInfo(
    val id: String,
    val resellerId: String,
    val siteId: String,
    val clientId: String,
    val guestInfo: GuestInfo
  )