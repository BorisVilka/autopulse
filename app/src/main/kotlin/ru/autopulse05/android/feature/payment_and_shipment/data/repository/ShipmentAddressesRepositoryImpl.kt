package ru.autopulse05.android.feature.payment_and_shipment.data.repository

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.payment_and_shipment.data.source.ShipmentAddressesDao
import ru.autopulse05.android.feature.payment_and_shipment.domain.model.ShipmentAddress
import ru.autopulse05.android.feature.payment_and_shipment.domain.repository.ShipmentAddressesRepository

@OptIn(DelicateCoroutinesApi::class)
class ShipmentAddressesRepositoryImpl(
  private val dao: ShipmentAddressesDao
) : ShipmentAddressesRepository {
  override suspend fun add(entity: ShipmentAddress) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.insert(entity)
    }
  }

  override suspend fun addAll(entities: List<ShipmentAddress>) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.insertAll(entities)
    }
  }

  override suspend fun update(entity: ShipmentAddress) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.update(entity)
    }
  }

  override suspend fun delete(entity: ShipmentAddress) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.delete(entity)
    }
  }

  override suspend fun deleteAll() {
    GlobalScope.launch(Dispatchers.IO) {
      dao.deleteAll()
    }
  }

  override fun getAll(): Flow<List<ShipmentAddress>> = dao.getAll()
}