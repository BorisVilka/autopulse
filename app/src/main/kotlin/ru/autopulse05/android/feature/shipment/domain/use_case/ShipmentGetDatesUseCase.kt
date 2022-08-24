package ru.autopulse05.android.feature.shipment.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.shipment.data.remote.ShipmentRemoteService
import ru.autopulse05.android.feature.shipment.data.remote.dto.toShipmentDate
import ru.autopulse05.android.feature.shipment.domain.model.ShipmentDate
import ru.autopulse05.android.shared.domain.util.Data

class ShipmentGetDatesUseCase(
  private val remoteService: ShipmentRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    minDeadlineTime: String,
    maxDeadlineTime: String,
    shipmentAddress: String? = null,
  ): Flow<Data<List<ShipmentDate>>> = flow {
    try {
      emit(Data.Loading())

      val shipmentDates = remoteService
        .getDates(
          login = login,
          passwordHash = passwordHash,
          minDeadlineTime = minDeadlineTime,
          maxDeadlineTime = maxDeadlineTime,
          shipmentAddress = shipmentAddress
        )
        .map { it.toShipmentDate() }

      emit(Data.Success(value = shipmentDates))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}