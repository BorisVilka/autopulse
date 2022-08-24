package ru.autopulse05.android.feature.product.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.product.data.remote.CrossImageMode
import ru.autopulse05.android.feature.product.data.remote.ProductRemoteService
import ru.autopulse05.android.feature.product.data.remote.dto.toProductInfo
import ru.autopulse05.android.feature.product.domain.model.ProductInfo
import ru.autopulse05.android.shared.domain.util.Data

class ProductGetInfoUseCase(
  private val remoteService: ProductRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    brand: String,
    number: String,
    locale: String,
    format: String = "bnpic",
    crossImage: Int = CrossImageMode.OFF,
    source: String? = null,
    withOriginal: String? = null,
  ): Flow<Data<ProductInfo>> = flow {
    try {
      emit(Data.Loading())

      val articleInfo = remoteService
        .getInfo(
          login = login,
          passwordHash = passwordHash,
          brand = brand,
          number = number,
          format = format,
          crossImage = crossImage,
          source = source,
          withOriginal = withOriginal,
          locale = locale,
        )
        .toProductInfo()

      emit(Data.Success(value = articleInfo))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}