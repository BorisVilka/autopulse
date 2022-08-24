package ru.autopulse05.android.feature.search.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.product.data.remote.dto.toProduct
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.search.data.remote.SearchRemoteService
import ru.autopulse05.android.shared.domain.util.Data

class SearchBatchUseCase(
  private val remoteService: SearchRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    search: String
  ): Flow<Data<List<Product>>> = flow {
    try {
      emit(Data.Loading())

      val products = remoteService
        .batch(
          login = login,
          passwordHash = passwordHash,
          search = search
        )
        .map { it.toProduct() }

      emit(Data.Success(value = products))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}