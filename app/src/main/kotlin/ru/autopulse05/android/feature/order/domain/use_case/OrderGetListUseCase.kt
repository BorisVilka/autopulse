package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.data.remote.dto.toTsOrder
import ru.autopulse05.android.feature.order.domain.model.TsOrder
import ru.autopulse05.android.shared.domain.util.Data

class OrderGetListUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<List<TsOrder>>> = flow {
    try {
      emit(Data.Loading())

      val tsOrders = remoteService
        .getList(
          login = login,
          passwordHash = passwordHash
        )
        .list
        .map { it.toTsOrder() }

      emit(Data.Success(value = tsOrders))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}