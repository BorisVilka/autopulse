package ru.autopulse05.android.feature.car.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.car.data.remote.CarRemoteService
import ru.autopulse05.android.feature.car.data.remote.dto.toCar
import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.shared.domain.util.Data

class CarGetFromGarageUseCase(
  private val remoteService: CarRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    carId: String
  ): Flow<Data<Car>> = flow {
    try {
      emit(Data.Loading())

      val car = remoteService
        .getCar(
          login = login,
          passwordHash = passwordHash,
          carId = carId
        )
        .toCar()

      emit(Data.Success(value = car))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}