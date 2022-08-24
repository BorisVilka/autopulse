package ru.autopulse05.android.core.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.autopulse05.android.feature.car.data.source.CarDao
import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.cart.data.source.CartDao
import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.shipment.data.source.ShipmentAddressesDao
import ru.autopulse05.android.feature.shipment.domain.model.ShipmentAddress
import ru.autopulse05.android.feature.user.data.source.UserDao
import ru.autopulse05.android.feature.user.domain.model.User

@Database(
  entities = [
    User::class,
    ShipmentAddress::class,
    CartItem::class,
    Car::class
  ],
  version = 1
)
abstract class AppDatabase : RoomDatabase() {
  companion object {
    const val NAME = "app_db"
  }

  fun clear() = this.clearAllTables()

  abstract val userDao: UserDao

  abstract val carDao: CarDao

  abstract val cartDao: CartDao

  abstract val shipmentAddressesDao: ShipmentAddressesDao
}