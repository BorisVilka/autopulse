package ru.autopulse05.android.feature.payment.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.autopulse05.android.feature.payment.data.remote.dto.PaymentMethodDtoList

interface PaymentRemoteService {

  @GET(PaymentHttpRoutes.METHODS)
  suspend fun getMethods(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String
  ): PaymentMethodDtoList

}