package ru.autopulse05.android.feature.shipment.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.shipment.domain.model.ShipmentAddress

interface ShipmentAddressesRepository {
  suspend fun add(entity: ShipmentAddress)
  suspend fun addAll(entities: List<ShipmentAddress>)
  suspend fun update(entity: ShipmentAddress)
  suspend fun delete(entity: ShipmentAddress)
  suspend fun deleteAll()
  fun getAll(): Flow<List<ShipmentAddress>>
}