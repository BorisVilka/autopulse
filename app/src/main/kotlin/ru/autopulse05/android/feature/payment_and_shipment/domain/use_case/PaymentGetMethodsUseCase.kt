package ru.autopulse05.android.feature.payment_and_shipment.domain.use_case

import android.app.Application
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.payment_and_shipment.data.remote.PaymentRemoteService
import ru.autopulse05.android.feature.payment_and_shipment.data.remote.dto.toPaymentMethod
import ru.autopulse05.android.feature.payment_and_shipment.domain.model.PaymentMethod
import ru.autopulse05.android.shared.domain.util.Data

class PaymentGetMethodsUseCase(
  private val remoteService: PaymentRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
  ): Flow<Data<List<PaymentMethod>>> = flow {
    try {
      emit(Data.Loading())

      val shipmentDates = remoteService
        .getMethods(
          login = login,
          passwordHash = passwordHash
        )
        .map { it.toPaymentMethod() }

      emit(Data.Success(value = shipmentDates))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}