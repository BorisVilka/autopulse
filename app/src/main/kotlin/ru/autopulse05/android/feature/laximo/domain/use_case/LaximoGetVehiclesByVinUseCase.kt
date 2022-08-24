package ru.autopulse05.android.feature.laximo.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoVehicle
import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetVehiclesByVinUseCase(
  private val remoteService: LaximoRemoteService
) {

  operator fun invoke(
    login: String,
    password: String,
    vin: String,
    locale: String
  ): Flow<Data<List<LaximoVehicle>>> = flow {
    try {
      emit(Data.Loading())

      val vehicles = remoteService
        .getVehiclesByVin(
          login = login,
          password = password,
          vin = vin,
          locale = locale
        )
        .map { it.toLaximoVehicle() }

      emit(Data.Success(value = vehicles))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}
