package ru.autopulse05.android.feature.cart.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.cart.data.remote.CartRemoteService
import ru.autopulse05.android.feature.cart.data.remote.dto.toCartItem
import ru.autopulse05.android.feature.cart.domain.model.Position
import ru.autopulse05.android.feature.cart.domain.model.toPositionDto
import ru.autopulse05.android.feature.cart.domain.repository.CartRepository
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.shared.data.remote.ResponseStatus
import ru.autopulse05.android.shared.domain.util.Data

class CartAddUseCase(
  private val repository: CartRepository,
  private val remoteService: CartRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    positions: Position
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      var status = ResponseStatus.SUCCESS

      val tmp = remoteService.add(
        login = login,
        passwordHash = passwordHash,
        brand = positions.brand,
        number = positions.number,
        itemKey = positions.itemKey,
        supplierCode = positions.supplierCode,
        quantity = positions.quantity
      )
      if(tmp.status!=ResponseStatus.SUCCESS) status = tmp.status

      if (status != ResponseStatus.ERROR) {
        val basketItems = remoteService
          .getContent(
            login = login,
            passwordHash = passwordHash
          )
          .map { it.toCartItem() }

        repository.deleteAll()
        repository.addAll(entities = basketItems)
        Log.d("TAG",basketItems.size.toString()+" "+repository.count().first())
        emit(Data.Success(value = Unit))
      } else {
        emit(Data.Error())
      }
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}