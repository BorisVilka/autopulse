package ru.autopulse05.android.feature.shipment.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.shipment.data.remote.ShipmentRemoteService
import ru.autopulse05.android.feature.shipment.data.remote.dto.toShipmentMethod
import ru.autopulse05.android.feature.shipment.domain.model.ShipmentMethod
import ru.autopulse05.android.shared.domain.util.Data

class ShipmentGetMethodsUseCase(
  private val remoteService: ShipmentRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<List<ShipmentMethod>>> = flow {
    try {
      emit(Data.Loading())

      val shipmentOffices = remoteService
        .getMethods(
          login = login,
          passwordHash = passwordHash
        )
        .map { it.toShipmentMethod() }

      emit(Data.Success(value = shipmentOffices))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}