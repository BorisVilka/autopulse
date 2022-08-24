package ru.autopulse05.android.feature.cart.data.repository

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.cart.data.source.CartDao
import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.cart.domain.repository.CartRepository

@OptIn(DelicateCoroutinesApi::class)
class CartRepositoryImpl(
  private val dao: CartDao
) : CartRepository {

  override suspend fun add(entity: CartItem) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.insert(entity)
    }.join()
  }
  override suspend fun addAll(entities: List<CartItem>) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.insertAll(entities)
    }.join()
  }

  override suspend fun update(entity: CartItem) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.update(entity)
    }.join()
  }

  override suspend fun delete(entity: CartItem) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.delete(entity)
    }.join()
  }

  override suspend fun deleteAll() {
    GlobalScope.launch(Dispatchers.IO) {
      dao.deleteAll()
    }.join()
  }

  override fun count(): Flow<Int> = dao.count()

  override fun getAll(): Flow<List<CartItem>> = dao.getAll()
}