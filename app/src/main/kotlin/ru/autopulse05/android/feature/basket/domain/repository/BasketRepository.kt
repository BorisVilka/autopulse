package ru.autopulse05.android.feature.basket.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.basket.domain.model.BasketItem

interface BasketRepository {
  suspend fun add(entity: BasketItem)
  suspend fun addAll(entities: List<BasketItem>)
  suspend fun update(entity: BasketItem)
  suspend fun delete(entity: BasketItem)
  suspend fun deleteAll()
  fun count(): Flow<Int>
  fun getAll(): Flow<List<BasketItem>>
}