package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.shared.domain.util.Data

class OrderCancelUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    positionId: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      remoteService
        .cancel(
          login = login,
          passwordHash = passwordHash,
          positionId = positionId
        )

      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}