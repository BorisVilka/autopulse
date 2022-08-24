package ru.autopulse05.android.feature.garage.data.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.garage.domain.model.Car

@Dao
interface GarageDao {
  @Query("SELECT * FROM cars")
  fun getAll(): Flow<List<Car>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(entities: List<Car>)

  @Query("SELECT COUNT(*) FROM cars")
  fun count(): Flow<Int>

  @Update
  suspend fun update(entity: Car)

  @Query("DELETE FROM cars")
  suspend fun deleteAll()
}