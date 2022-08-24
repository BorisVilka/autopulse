package ru.autopulse05.android.feature.cart.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.cart.domain.model.CartItem

interface CartRepository {
  suspend fun add(entity: CartItem)
  suspend fun addAll(entities: List<CartItem>)
  suspend fun update(entity: CartItem)
  suspend fun delete(entity: CartItem)
  suspend fun deleteAll()
  fun count(): Flow<Int>
  fun getAll(): Flow<List<CartItem>>
}