package ru.autopulse05.android.feature.basket.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.basket.data.remote.BasketRemoteService
import ru.autopulse05.android.feature.basket.data.remote.dto.toBasketOptionsItem
import ru.autopulse05.android.feature.basket.domain.model.BasketOptionsItem
import ru.autopulse05.android.shared.domain.util.Data

class BasketGetOptionsUseCase(
  private val remoteService: BasketRemoteService
) {

  suspend operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<List<BasketOptionsItem>>> = flow {
    try {
      emit(Data.Loading())

      val basketOptions = remoteService
        .getOptions(
          login = login,
          passwordHash = passwordHash
        )
        .map { it.toBasketOptionsItem() }

      emit(Data.Success(value = basketOptions))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}