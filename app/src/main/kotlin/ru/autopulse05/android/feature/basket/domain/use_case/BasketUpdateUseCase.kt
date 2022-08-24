package ru.autopulse05.android.feature.basket.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.basket.data.remote.BasketRemoteService
import ru.autopulse05.android.feature.basket.data.remote.dto.toBasketItem
import ru.autopulse05.android.feature.basket.domain.repository.BasketRepository
import ru.autopulse05.android.shared.domain.util.Data

class BasketUpdateUseCase(
  private val remoteService: BasketRemoteService
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
        .map { it.toBasketItem() }

      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}