package ru.autopulse05.android.feature.vin.data.remote.dto

import ru.autopulse05.android.feature.vin.domain.model.CarInfo

data class VinDto (
    val createdDateTime: Long,
    val clientId: String?,
    val _id: Int,
    val state: String,
    val carInfo: CarInfo?,
    val parts: List<PartDto>?,
    val expertReply: String?,
    val chat: List<ChatDto>?
)