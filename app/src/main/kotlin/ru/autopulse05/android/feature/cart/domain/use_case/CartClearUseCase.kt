package ru.autopulse05.android.feature.cart.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.cart.data.remote.CartRemoteService
import ru.autopulse05.android.feature.cart.data.remote.dto.PositionDto
import ru.autopulse05.android.feature.cart.data.remote.dto.toCartItem
import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.cart.domain.repository.CartRepository
import ru.autopulse05.android.shared.data.remote.ResponseStatus
import ru.autopulse05.android.shared.domain.util.Data

class CartClearUseCase(
  private val repository: CartRepository,
  private val remoteService: CartRemoteService
) {


  operator fun invoke(
    login: String,
    passwordHash: String,
    items: List<CartItem>
  ): Flow<Data<List<CartItem>>> = flow {
    try {
      emit(Data.Loading())

      var status = ResponseStatus.SUCCESS

      for(i in items.indices) {
        val tmp = remoteService.add(
          login = login,
          passwordHash = passwordHash,
          brand = items[i].brand,
          number = items[i].number,
          itemKey = items[i].itemKey,
          supplierCode = items[i].supplierCode,
          quantity = 0
        )
        if(tmp.status!=ResponseStatus.SUCCESS) status = tmp.status
      }

      if (status != ResponseStatus.ERROR) {
        val basketItems = remoteService
          .getContent(
            login = login,
            passwordHash = passwordHash
          )
          .map { it.toCartItem() }

        repository.deleteAll()
        repository.addAll(entities = basketItems)

        emit(Data.Success(value = basketItems))
      } else {
        emit(Data.Error())
      }
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}