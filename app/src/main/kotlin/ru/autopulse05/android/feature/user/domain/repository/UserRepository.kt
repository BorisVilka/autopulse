package ru.autopulse05.android.feature.user.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.user.domain.model.User

interface UserRepository {
  fun get(): Flow<User?>
  suspend fun update(entity: User)
  suspend fun set(entity: User)
  suspend fun delete(entity: User)
}