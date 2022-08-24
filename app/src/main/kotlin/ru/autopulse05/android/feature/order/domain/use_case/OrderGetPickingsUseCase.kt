package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.data.remote.dto.toPicking
import ru.autopulse05.android.feature.order.domain.model.Picking
import ru.autopulse05.android.shared.domain.util.Data

class OrderGetPickingsUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    opId: String
  ): Flow<Data<List<Picking>>> = flow {
    try {
      emit(Data.Loading())

      val pickings = remoteService
        .getPickings(
          login = login,
          passwordHash = passwordHash,
          opId = opId
        )
        .map { it.toPicking() }

      emit(Data.Success(value = pickings))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}