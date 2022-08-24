package ru.autopulse05.android.feature.product.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.product.data.remote.ProductRemoteService
import ru.autopulse05.android.feature.product.data.remote.dto.toAdvices
import ru.autopulse05.android.feature.product.domain.model.Advices
import ru.autopulse05.android.feature.product.domain.model.Article
import ru.autopulse05.android.feature.product.domain.model.toArticleDto
import ru.autopulse05.android.shared.domain.util.Data

class ProductAdvicesBatchUseCase(
  private val remoteService: ProductRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    articles: List<Article>,
    limit: Int? = null,
  ): Flow<Data<Advices>> = flow {
    try {
      emit(Data.Loading())

      val advices = remoteService
        .advicesBatch(
          login = login,
          passwordHash = passwordHash,
          articles = articles.map { it.toArticleDto() },
          limit = limit,
        )
        .toAdvices()

      emit(Data.Success(value = advices))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}