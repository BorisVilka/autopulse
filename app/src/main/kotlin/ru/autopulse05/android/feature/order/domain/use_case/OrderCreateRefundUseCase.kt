package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.domain.model.RefundPosition
import ru.autopulse05.android.feature.order.domain.model.toRefundPositionDto
import ru.autopulse05.android.shared.domain.util.Data

class OrderCreateRefundUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    orderPickingId: String,
    positions: List<RefundPosition>
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      remoteService
        .createComplaint(
          login = login,
          passwordHash = passwordHash,
          orderPickingId = orderPickingId,
          positions = positions.map { it.toRefundPositionDto() },
        )


      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}