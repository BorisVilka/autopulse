package ru.autopulse05.android.feature.garage.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.garage.data.remote.GarageRemoteService
import ru.autopulse05.android.feature.garage.data.remote.dto.toCar
import ru.autopulse05.android.feature.garage.domain.model.Car
import ru.autopulse05.android.shared.domain.util.Data

class GarageGetCarUseCase(
  private val remoteService: GarageRemoteService
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