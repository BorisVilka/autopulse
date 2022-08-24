package ru.autopulse05.android.feature.basket.data.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.basket.domain.model.BasketItem

@Dao
interface BasketDao {
  @Query("SELECT * FROM basket_items")
  fun getAll(): Flow<List<BasketItem>>

  @Query("SELECT COUNT(*) FROM basket_items")
  fun count(): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(entities: List<BasketItem>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: BasketItem)

  @Update
  suspend fun update(entity: BasketItem)

  @Delete
  suspend fun delete(entity: BasketItem)

  @Query("DELETE FROM basket_items")
  suspend fun deleteAll()
}