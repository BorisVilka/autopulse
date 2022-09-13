package ru.autopulse05.android.feature.order.data.remote.dto

data class StatusDto(
    val status: String,
    val id: String,
    val confirmation: ConfirmationDto
)