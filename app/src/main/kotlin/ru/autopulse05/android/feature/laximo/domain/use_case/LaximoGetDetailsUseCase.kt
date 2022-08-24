package ru.autopulse05.android.feature.laximo.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoDetail
import ru.autopulse05.android.feature.laximo.domain.model.LaximoDetail
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetDetailsUseCase(
  private val remoteService: LaximoRemoteService
) {

  operator fun invoke(
    login: String,
    password: String,
    catalog: String,
    unitId: String,
    ssd: String,
    locale: String
  ): Flow<Data<List<LaximoDetail>>> = flow {
    try {
      emit(Data.Loading())

      val catalogs = remoteService
        .getDetails(
          login = login,
          password = password,
          catalog = catalog,
          unitId = unitId,
          ssd = ssd,
          locale = locale
        )
        .map { it.toLaximoDetail() }

      emit(Data.Success(value = catalogs))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}
