package ru.autopulse05.android.feature.laximo.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoUnit
import ru.autopulse05.android.feature.laximo.domain.model.LaximoUnit
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetUnitUseCase(
  private val remoteService: LaximoRemoteService
) {

  operator fun invoke(
    login: String,
    password: String,
    catalog: String,
    unitId: String,
    ssd: String,
    locale: String
  ): Flow<Data<LaximoUnit>> = flow {
    try {
      emit(Data.Loading())

      val unit = remoteService
        .getUnit(
          login = login,
          password = password,
          catalog = catalog,
          unitId = unitId,
          ssd = ssd,
          locale = locale
        )
        .toLaximoUnit()

      emit(Data.Success(value = unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}
