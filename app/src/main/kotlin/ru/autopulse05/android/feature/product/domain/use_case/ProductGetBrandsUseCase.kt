package ru.autopulse05.android.feature.product.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.product.data.remote.ProductRemoteService
import ru.autopulse05.android.feature.product.data.remote.dto.toBrand
import ru.autopulse05.android.feature.product.domain.model.Brand
import ru.autopulse05.android.shared.domain.util.Data

class ProductGetBrandsUseCase(
  private val remoteService: ProductRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<List<Brand>>> = flow {
    try {
      emit(Data.Loading())

      val brandList = remoteService
        .getBrands(
          login = login,
          passwordHash = passwordHash
        )
        .map { it.toBrand() }

      emit(Data.Success(value = brandList))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}