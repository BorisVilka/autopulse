package ru.autopulse05.android.feature.car.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.car.data.remote.CarRemoteService
import ru.autopulse05.android.feature.car.data.remote.dto.toCar
import ru.autopulse05.android.feature.car.domain.repository.CarRepository
import ru.autopulse05.android.shared.domain.util.Data

class CarDeleteFromGarageUseCase(
  private val repository: CarRepository,
  private val remoteService: CarRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    carId: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      remoteService.deleteCar(
        login = login,
        passwordHash = passwordHash,
        carId = carId
      )

      val garageCars = remoteService
        .getCars(
          login = login,
          passwordHash = passwordHash
        )
        .map { it.toCar() }

      repository.deleteAll()
      repository.addAll(garageCars)

      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}