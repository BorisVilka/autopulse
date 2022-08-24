package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.data.remote.dto.toOrder
import ru.autopulse05.android.feature.order.domain.model.Order
import ru.autopulse05.android.shared.domain.util.Data

class OrderGetAllUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<List<Order>>> = flow {
    try {
      emit(Data.Loading())

      val orders = remoteService
        .get(
          login = login,
          passwordHash = passwordHash
        )
        .items
        .map { it.toOrder() }

      emit(Data.Success(value = orders))
    } catch (e: HttpException) {
      emit(Data.Error(message = e.message, code = e.code()))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}