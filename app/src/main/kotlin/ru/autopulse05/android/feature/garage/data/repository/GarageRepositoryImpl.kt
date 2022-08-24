package ru.autopulse05.android.feature.garage.data.repository

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.garage.data.source.GarageDao
import ru.autopulse05.android.feature.garage.domain.model.Car
import ru.autopulse05.android.feature.garage.domain.repository.GarageRepository

@OptIn(DelicateCoroutinesApi::class)
class GarageRepositoryImpl(
  private val dao: GarageDao
) : GarageRepository {
  override suspend fun addAll(entities: List<Car>) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.insertAll(entities)
    }
  }

  override suspend fun update(entity: Car) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.update(entity)
    }
  }

  override suspend fun deleteAll() {
    GlobalScope.launch(Dispatchers.IO) {
      dao.deleteAll()
    }
  }

  override fun count(): Flow<Int> = dao.count()

  override fun getAll(): Flow<List<Car>> = dao.getAll()
}