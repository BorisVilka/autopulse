package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.domain.model.ComplaintPosition
import ru.autopulse05.android.feature.order.domain.model.toComplaintPositionDto
import ru.autopulse05.android.shared.domain.util.Data

class OrderCreateComplaintUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    orderPickingId: String,
    positions: List<ComplaintPosition>
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())




      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}