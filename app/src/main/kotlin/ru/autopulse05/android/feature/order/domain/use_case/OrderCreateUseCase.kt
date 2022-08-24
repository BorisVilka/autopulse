package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.data.remote.dto.toTsOrder
import ru.autopulse05.android.feature.order.domain.model.TsOrder
import ru.autopulse05.android.shared.domain.util.Data

class OrderCreateUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    number: String,
    createTime: String,
    positionIds: List<String>,
    deliveryMethodId: String,
    address: String,
    person: String,
    contact: String,
    comment: String,
  ): Flow<Data<TsOrder>> = flow {
    try {
      emit(Data.Loading())

      val tsOrder = remoteService
        .create(
          login = login,
          passwordHash = passwordHash,
          number = number,
          createTime = createTime,
          positionIds = positionIds,
          deliveryMethodId = deliveryMethodId,
          address = address,
          person = person,
          contact = contact,
          comment = comment,
        )
        .toTsOrder()

      emit(Data.Success(value = tsOrder))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}