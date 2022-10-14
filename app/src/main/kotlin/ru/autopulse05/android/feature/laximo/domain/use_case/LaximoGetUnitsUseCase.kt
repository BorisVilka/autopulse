package ru.autopulse05.android.feature.laximo.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoUnit
import ru.autopulse05.android.feature.laximo.domain.model.LaximoUnit
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetUnitsUseCase(
  private val remoteService: LaximoRemoteService
) {

  operator fun invoke(
    login: String,
    password: String,
    catalog: String,
    vehicleId: String,
    categoryId: String = "",
    ssd: String,
    locale: String
  ): Flow<Data<List<LaximoUnit>>> = flow {
    try {
      emit(Data.Loading())

      val units = remoteService
        .getUnits(
          login = login,
          password = password,
          catalog = catalog,
          vehicleId = vehicleId,
          categoryId = categoryId,
          ssd = ssd,
          locale = locale
        )
        .map { it.toLaximoUnit() }

      emit(Data.Success(value = units))
    } catch (e: Exception) {
      e.printStackTrace()
      emit(Data.Error(message = e.message))
    }
  }
}
