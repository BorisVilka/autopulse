package ru.autopulse05.android.feature.user.data.remote.dto

data class PaymentDto (
        val createDateTime: String,
        val paymentType: String,
        val paymentId: String,
        val amount: String,
        val comment: String,
        )