package ru.autopulse05.android.feature.car.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.car.data.remote.CarRemoteService
import ru.autopulse05.android.feature.car.data.remote.dto.toCar
import ru.autopulse05.android.feature.car.domain.repository.CarRepository
import ru.autopulse05.android.shared.domain.util.Data

class CarAddToGarageCarUseCase(
  private val repository: CarRepository,
  private val remoteService: CarRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    name: String,
    comment: String = "",
    year: Int,
    vin: String,
    frame: String,
    mileage: String,
    manufacturerId: Int,
    modelId: Int,
    modificationId: Int,
    vehicleRegPlate: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      remoteService.addCar(
        login = login,
        passwordHash = passwordHash,
        name = name,
        comment = comment,
        year = year,
        vin = vin,
        frame = frame,
        mileage = mileage,
        manufacturerId = manufacturerId,
        modelId = modelId,
        modificationId = modificationId,
        vehicleRegPlate = vehicleRegPlate
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