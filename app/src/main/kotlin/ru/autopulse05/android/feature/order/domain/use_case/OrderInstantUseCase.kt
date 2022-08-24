package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.cart.domain.model.Position
import ru.autopulse05.android.feature.cart.domain.model.toPositionDto
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.shared.data.remote.ResponseStatus
import ru.autopulse05.android.shared.data.remote.WholeOrderMode
import ru.autopulse05.android.shared.domain.util.Data

class OrderInstantUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    positions: List<Position>,
    paymentMethod: String,
    shipmentMethod: String,
    shipmentAddress: String,
    shipmentOffice: String,
    shipmentDate: String,
    comment: String,
    wholeOrderMode: Int = WholeOrderMode.OFF
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val ordersDto = remoteService
        .instant(
          login = login,
          passwordHash = passwordHash,
          positions = positions.map { it.toPositionDto() },
          paymentMethod = paymentMethod,
          shipmentMethod = shipmentMethod,
          shipmentAddress = shipmentAddress,
          shipmentOffice = shipmentOffice,
          shipmentDate = shipmentDate,
          comment = comment,
          wholeOrderMode = wholeOrderMode,
        )

      if (ordersDto.status != ResponseStatus.ERROR) Data.Success(value = Unit)
      else Data.Error(message = ordersDto.errorMessage)
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}