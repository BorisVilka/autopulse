package ru.autopulse05.android.feature.shipment.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.shipment.data.remote.ShipmentRemoteService
import ru.autopulse05.android.feature.shipment.data.remote.dto.toShipmentAddress
import ru.autopulse05.android.feature.shipment.domain.repository.ShipmentAddressesRepository
import ru.autopulse05.android.shared.domain.util.Data

class ShipmentAddAddressUseCase(
  private val repository: ShipmentAddressesRepository,
  private val remoteService: ShipmentRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    address: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      remoteService.addAddress(
        login = login,
        passwordHash = passwordHash,
        address = address
      )

      val shipmentAddresses = remoteService
        .getAddresses(
          login = login,
          passwordHash = passwordHash
        )
        .map { it.toShipmentAddress() }

      repository.deleteAll()
      repository.addAll(shipmentAddresses)

      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}