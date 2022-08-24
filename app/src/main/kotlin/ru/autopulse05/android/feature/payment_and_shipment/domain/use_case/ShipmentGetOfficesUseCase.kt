package ru.autopulse05.android.feature.payment_and_shipment.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.payment_and_shipment.data.remote.ShipmentRemoteService
import ru.autopulse05.android.feature.payment_and_shipment.data.remote.dto.toShipmentOffice
import ru.autopulse05.android.feature.payment_and_shipment.domain.model.ShipmentOffice
import ru.autopulse05.android.shared.domain.util.Data

class ShipmentGetOfficesUseCase(
  private val remoteService: ShipmentRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    officesType: String? = null
  ): Flow<Data<List<ShipmentOffice>>> = flow {
    try {
      emit(Data.Loading())

      val shipmentOffices = remoteService
        .getOffices(
          login = login,
          passwordHash = passwordHash,
          officesType = officesType
        )
        .map { it.toShipmentOffice() }

      emit(Data.Success(value = shipmentOffices))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}