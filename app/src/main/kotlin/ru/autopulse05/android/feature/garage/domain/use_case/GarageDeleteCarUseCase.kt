package ru.autopulse05.android.feature.garage.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.garage.data.remote.GarageRemoteService
import ru.autopulse05.android.feature.garage.data.remote.dto.toCar
import ru.autopulse05.android.feature.garage.domain.repository.GarageRepository
import ru.autopulse05.android.shared.domain.util.Data

class GarageDeleteCarUseCase(
  private val remoteService: GarageRemoteService
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


      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}