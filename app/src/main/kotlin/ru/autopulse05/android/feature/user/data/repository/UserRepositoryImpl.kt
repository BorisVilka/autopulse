package ru.autopulse05.android.feature.user.data.repository

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.user.data.source.UserDao
import ru.autopulse05.android.feature.user.domain.model.User
import ru.autopulse05.android.feature.user.domain.repository.UserRepository

@OptIn(DelicateCoroutinesApi::class)
class UserRepositoryImpl(
  private val dao: UserDao
) : UserRepository {
  override fun get(): Flow<User?> = dao.getAll().map { it.firstOrNull() }

  override suspend fun update(entity: User) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.update(entity)
    }
  }

  override suspend fun set(entity: User) {
    get().first().let {
      if (it != null) delete(it)
    }

    GlobalScope.launch(Dispatchers.IO) {
      dao.insert(entity)
    }
  }

  override suspend fun delete(entity: User) {
    GlobalScope.launch(Dispatchers.IO) {
      dao.delete(entity)
    }
  }
}