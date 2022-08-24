package ru.autopulse05.android.feature.cart.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.cart.data.remote.CartRemoteService
import ru.autopulse05.android.feature.cart.data.remote.dto.toCartItem
import ru.autopulse05.android.feature.cart.domain.repository.CartRepository
import ru.autopulse05.android.shared.domain.util.Data

class CartUpdateUseCase(
  private val repository: CartRepository,
  private val remoteService: CartRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val basketItems = remoteService
        .getContent(
          login = login,
          passwordHash = passwordHash
        )
        .map { it.toCartItem() }

      repository.deleteAll()
      repository.addAll(entities = basketItems)

      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}