package ru.autopulse05.android.feature.cart.data.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.cart.domain.model.CartItem

@Dao
interface CartDao {
  @Query("SELECT * FROM cart_items")
  fun getAll(): Flow<List<CartItem>>

  @Query("SELECT COUNT(*) FROM cart_items")
  fun count(): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(entities: List<CartItem>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: CartItem)

  @Update
  suspend fun update(entity: CartItem)

  @Delete
  suspend fun delete(entity: CartItem)

  @Query("DELETE FROM cart_items")
  suspend fun deleteAll()
}