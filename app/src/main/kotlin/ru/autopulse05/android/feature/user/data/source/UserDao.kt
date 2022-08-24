package ru.autopulse05.android.feature.user.data.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.user.domain.model.User

@Dao
interface UserDao {
  @Query("SELECT * FROM users")
  fun getAll(): Flow<List<User>>

  @Update
  suspend fun update(entity: User)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: User)

  @Delete
  suspend fun delete(entity: User)
}