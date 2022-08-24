package ru.autopulse05.android.feature.garage.domain.use_case

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.garage.data.remote.GarageRemoteService
import ru.autopulse05.android.feature.garage.data.remote.dto.toCar
import ru.autopulse05.android.feature.garage.domain.repository.GarageRepository
import ru.autopulse05.android.shared.domain.util.Data

@OptIn(DelicateCoroutinesApi::class)
class GarageUpdateCarUseCase(
 private val remoteService: GarageRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    carId: String,
    name: String,
    comment: String,
    year: String,
    vin: String,
    frame: String,
    mileage: String,
    manufacturerId: String,
    modelId: String,
    modificationId: String,
    vehicleRegPlate: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      remoteService.updateCar(
        login = login,
        passwordHash = passwordHash,
        carId = carId,
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


      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}