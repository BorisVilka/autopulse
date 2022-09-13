package ru.autopulse05.android.feature.order.data.remote

import retrofit2.http.*
import ru.autopulse05.android.feature.order.data.remote.dto.ConfirmationDto
import ru.autopulse05.android.feature.order.data.remote.dto.KassaType
import ru.autopulse05.android.feature.order.data.remote.dto.StatusDto
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount

interface PayService {

    @POST("/v3/payments")
   suspend fun get(
        @Body body: BOdyObject
        ): StatusDto
    @GET("/v3/payments/{id}")
    suspend fun paymentInfo(@Path("id") id: String): StatusDto
}
data class BOdyObject(
    val amount: amount,
    val payment_token: String,
    val capture: Boolean = true,
    val description: String = "ORDER",
)
data class PaymentData(
    val type: String
)
data class amount (
    val value: String,
    val currency: String
    )