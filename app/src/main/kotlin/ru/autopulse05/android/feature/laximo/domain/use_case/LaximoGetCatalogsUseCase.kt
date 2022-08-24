package ru.autopulse05.android.feature.laximo.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoCatalog
import ru.autopulse05.android.feature.laximo.domain.model.LaximoCatalog
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetCatalogsUseCase(
  private val remoteService: LaximoRemoteService
) {

  operator fun invoke(
    login: String,
    password: String,
    locale: String
  ): Flow<Data<List<LaximoCatalog>>> = flow {
    try {
      emit(Data.Loading())

      val catalogs = remoteService
        .getCatalogs(
          login = login,
          password = password,
          locale = locale
        )
        .map { it.toLaximoCatalog() }

      emit(Data.Success(value = catalogs))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}
