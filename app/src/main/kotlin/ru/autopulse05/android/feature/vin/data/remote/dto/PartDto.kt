package ru.autopulse05.android.feature.vin.data.remote.dto

data class PartDto(
    val query: String,
    val offers: List<OfferDto>?
)