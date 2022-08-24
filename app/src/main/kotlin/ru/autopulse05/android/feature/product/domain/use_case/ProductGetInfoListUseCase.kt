package ru.autopulse05.android.feature.product.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.product.data.remote.CrossImageMode
import ru.autopulse05.android.feature.product.data.remote.ProductRemoteService
import ru.autopulse05.android.feature.product.data.remote.dto.toProductInfo
import ru.autopulse05.android.feature.product.domain.model.ProductInfo
import ru.autopulse05.android.shared.domain.util.Data

class ProductGetInfoListUseCase(
  private val remoteService: ProductRemoteService
) {


}