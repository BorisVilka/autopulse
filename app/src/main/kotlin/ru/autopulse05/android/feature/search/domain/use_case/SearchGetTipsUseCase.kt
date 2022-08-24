package ru.autopulse05.android.feature.search.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.search.data.remote.SearchRemoteService
import ru.autopulse05.android.feature.search.data.remote.dto.toSearchTip
import ru.autopulse05.android.feature.search.domain.model.SearchTip
import ru.autopulse05.android.shared.domain.util.Data

class SearchGetTipsUseCase(
  private val remoteService: SearchRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    number: String,
    locale: String
  ): Flow<Data<List<SearchTip>>> = flow {
    try {
      emit(Data.Loading())

      val tips = remoteService
        .getTips(
          login = login,
          passwordHash = passwordHash,
          number = number,
          locale = locale
        )
        .map { it.toSearchTip() }

      emit(Data.Success(value = tips))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}