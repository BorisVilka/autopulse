package ru.autopulse05.android.feature.payment_and_shipment.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.payment_and_shipment.data.remote.ShipmentRemoteService
import ru.autopulse05.android.feature.payment_and_shipment.data.remote.dto.toShipmentAddress
import ru.autopulse05.android.feature.payment_and_shipment.domain.model.ShipmentAddress
import ru.autopulse05.android.feature.payment_and_shipment.domain.repository.ShipmentAddressesRepository
import ru.autopulse05.android.shared.domain.util.Data

class ShipmentUpdateAddressesUseCase(
  private val repository: ShipmentAddressesRepository,
  private val remoteService: ShipmentRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<List<ShipmentAddress>>> = flow {
    try {
      emit(Data.Loading())

      val shipmentAddresses = remoteService
        .getAddresses(
          login = login,
          passwordHash = passwordHash
        )
        .map { it.toShipmentAddress() }

      repository.deleteAll()
      repository.addAll(shipmentAddresses)

      Data.Success(value = Unit)
    } catch (e: Exception) {
      Data.Error(message = e.message)
    }
  }
}