package ru.autopulse05.android.feature.search.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.autopulse05.android.feature.product.data.remote.dto.toProduct
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.search.data.remote.AnalogsMode
import ru.autopulse05.android.feature.search.data.remote.OnlineFilteringMode
import ru.autopulse05.android.feature.search.data.remote.OnlineStocksMode
import ru.autopulse05.android.feature.search.data.remote.SearchRemoteService
import ru.autopulse05.android.shared.domain.util.Data

class SearchGetArticlesUseCase(
  private val remoteService: SearchRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    number: String,
    brand: String,
    onlineStocks: Int = OnlineStocksMode.ON,
    onlineFiltering: Int = OnlineFilteringMode.OFF,
    analogs: Int = AnalogsMode.ON
  ): Flow<Data<List<Product>>> = flow {
    try {
      emit(Data.Loading())

      val products = remoteService
        .getArticles(
          login = login,
          passwordHash = passwordHash,
          number = number,
          brand = brand,
          onlineStocks = onlineStocks,
          onlineFiltering = onlineFiltering,
          analogs = analogs
        )
        .map { it.toProduct() }

      emit(Data.Success(value = products))
    } catch (e: HttpException) {
      emit(Data.Error(message = e.message, code = e.code()))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}