package ru.autopulse05.android.feature.basket.data.repository

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.basket.data.source.BasketDao
import ru.autopulse05.android.feature.basket.domain.model.BasketItem
import ru.autopulse05.android.feature.basket.domain.repository.BasketRepository

@OptIn(DelicateCoroutinesApi::class)
class BasketRepositoryImpl(
  private val dao: BasketDao
) : BasketRepository {

  override suspend fun add(entity: BasketItem) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.insert(entity)
    }.join()
  }
  override suspend fun addAll(entities: List<BasketItem>) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.insertAll(entities)
    }.join()
  }

  override suspend fun update(entity: BasketItem) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.update(entity)
    }.join()
  }

  override suspend fun delete(entity: BasketItem) {
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

  override fun getAll(): Flow<List<BasketItem>> = dao.getAll()
}