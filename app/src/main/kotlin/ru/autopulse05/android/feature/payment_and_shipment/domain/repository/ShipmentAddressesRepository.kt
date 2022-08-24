package ru.autopulse05.android.feature.payment_and_shipment.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.basket.domain.model.BasketItem
import ru.autopulse05.android.feature.payment_and_shipment.domain.model.ShipmentAddress

interface ShipmentAddressesRepository {
  suspend fun add(entity: ShipmentAddress)
  suspend fun addAll(entities: List<ShipmentAddress>)
  suspend fun update(entity: ShipmentAddress)
  suspend fun delete(entity: ShipmentAddress)
  suspend fun deleteAll()
  fun getAll(): Flow<List<ShipmentAddress>>
}