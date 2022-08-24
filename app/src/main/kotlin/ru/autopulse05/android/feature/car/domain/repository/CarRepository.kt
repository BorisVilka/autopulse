package ru.autopulse05.android.feature.car.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.car.domain.model.Car

interface CarRepository {
  suspend fun addAll(entities: List<Car>)
  suspend fun update(entity: Car)
  suspend fun deleteAll()
  fun count(): Flow<Int>
  fun getAll(): Flow<List<Car>>
}