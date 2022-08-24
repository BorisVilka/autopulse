package ru.autopulse05.android.feature.search.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.search.data.remote.SearchRemoteService
import ru.autopulse05.android.feature.search.data.remote.dto.toHistoryItem
import ru.autopulse05.android.feature.search.domain.model.HistoryItem
import ru.autopulse05.android.shared.domain.util.Data

class SearchGetHistoryUseCase(
  private val remoteService: SearchRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<List<HistoryItem>>> = flow {
    try {
      emit(Data.Loading())

      val historyItems = remoteService
        .getHistory(
          login = login,
          passwordHash = passwordHash
        )
        .map { it.toHistoryItem() }

      emit(Data.Success(value = historyItems))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}