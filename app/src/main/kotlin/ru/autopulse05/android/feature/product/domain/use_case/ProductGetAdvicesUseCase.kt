package ru.autopulse05.android.feature.product.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.product.data.remote.ProductRemoteService
import ru.autopulse05.android.feature.product.data.remote.dto.toAdvice
import ru.autopulse05.android.feature.product.domain.model.Advice
import ru.autopulse05.android.shared.domain.util.Data

class ProductGetAdvicesUseCase(
  private val remoteService: ProductRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    brand: String,
    number: String,
    limit: Int? = null
  ): Flow<Data<List<Advice>>> = flow {
    try {
      emit(Data.Loading())

      val adviceList = remoteService
        .getAdvices(
          login = login,
          passwordHash = passwordHash,
          brand = brand,
          number = number,
          limit = limit
        )
        .map { it.toAdvice() }

      emit(Data.Success(value = adviceList))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}