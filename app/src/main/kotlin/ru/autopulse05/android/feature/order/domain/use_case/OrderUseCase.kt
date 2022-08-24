package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.domain.model.WholeOrderMode
import ru.autopulse05.android.shared.data.remote.ResponseStatus
import ru.autopulse05.android.shared.domain.util.Data

class OrderUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    paymentMethod: String,
    shipmentMethod: String,
    shipmentAddress: String?,
    shipmentOffice: String?,
    shipmentDate: String?,
    comment: String,
    wholeOrderMode: WholeOrderMode,
    positionIds: Array<Int>
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val createOrdersDto = remoteService
        .order(
          login = login,
          passwordHash = passwordHash,
          paymentMethod = paymentMethod,
          shipmentMethod = shipmentMethod,
          shipmentAddress = shipmentAddress,
          shipmentOffice = shipmentOffice,
          shipmentDate = shipmentDate,
          comment = comment,
          wholeOrderMode = wholeOrderMode.value,
          positionIds = positionIds
        )

      val data = when (createOrdersDto.status) {
        ResponseStatus.ERROR -> Data.Error(message = createOrdersDto.errorMessage)
        else -> Data.Success(value = Unit)
      }

     emit(data)
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}