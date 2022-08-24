package ru.autopulse05.android.feature.shipment.data.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.autopulse05.android.feature.shipment.domain.model.ShipmentAddress

@Dao
interface ShipmentAddressesDao {
  @Query("SELECT * FROM shipment_addresses")
  fun getAll(): Flow<List<ShipmentAddress>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(entities: List<ShipmentAddress>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: ShipmentAddress)

  @Update
  suspend fun update(entity: ShipmentAddress)

  @Delete
  suspend fun delete(entity: ShipmentAddress)

  @Query("DELETE FROM shipment_addresses")
  suspend fun deleteAll()
}