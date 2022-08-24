package ru.autopulse05.android.feature.garage.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.garage.domain.model.Car

interface GarageRepository {
  suspend fun addAll(entities: List<Car>)
  suspend fun update(entity: Car)
  suspend fun deleteAll()
  fun count(): Flow<Int>
  fun getAll(): Flow<List<Car>>
}