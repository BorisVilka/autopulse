package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.data.remote.dto.toRefund
import ru.autopulse05.android.feature.order.domain.model.Refund
import ru.autopulse05.android.shared.domain.util.Data

class OrderGetRefundsUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    opId: String,
    status: Int,
    type: Int
  ): Flow<Data<List<Refund>>> = flow {
    try {
      emit(Data.Loading())

      val refunds = remoteService
        .getRefunds(
          login = login,
          passwordHash = passwordHash,
          opId = opId,
          status = status,
          type = type
        )
        .map { it.toRefund() }

      emit(Data.Success(value = refunds))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}