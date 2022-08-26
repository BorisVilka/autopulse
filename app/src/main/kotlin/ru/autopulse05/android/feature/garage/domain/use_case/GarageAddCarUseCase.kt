package ru.autopulse05.android.feature.garage.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.garage.data.remote.GarageRemoteService
import ru.autopulse05.android.feature.garage.data.remote.dto.toCar
import ru.autopulse05.android.feature.garage.domain.repository.GarageRepository
import ru.autopulse05.android.shared.domain.util.Data

class GarageAddCarUseCase(
  private val remoteService: GarageRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    name: String?,
    comment: String?,
    year: Int?,
    vin: String?,
    frame: String?,
    mileage: String?,
    manufacturerId: Int?,
    modelId: Int?,
    modificationId: Int?,
    vehicleRegPlate: String?
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val ans = remoteService.addCar(
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
      Log.d("TAG",ans.errorMessage+" "+ans.vin)
      if(ans.errorMessage!=null) emit(Data.Error(message = ans.errorMessage))
      else emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      e.printStackTrace()
      emit(Data.Error(message = e.message))
    }
  }
}