package ru.autopulse05.android.feature.basket.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.basket.data.remote.BasketRemoteService
import ru.autopulse05.android.feature.basket.data.remote.dto.toBasketItem
import ru.autopulse05.android.feature.basket.domain.model.Position
import ru.autopulse05.android.feature.basket.domain.model.toPositionDto
import ru.autopulse05.android.feature.basket.domain.repository.BasketRepository
import ru.autopulse05.android.shared.data.remote.ResponseStatus
import ru.autopulse05.android.shared.domain.util.Data

class BasketAddUseCase(
  private val remoteService: BasketRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    positions: List<Position>
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val basketAddDto = remoteService.add(
        login = login,
        passwordHash = passwordHash,
        positions = positions.map { it.toPositionDto() }
      )

      if (basketAddDto.status != ResponseStatus.ERROR) {
        val basketItems = remoteService
          .getContent(
            login = login,
            passwordHash = passwordHash
          )
          .map { it.toBasketItem() }


        emit(Data.Success(value = Unit))
      } else {
        emit(Data.Error())
      }
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }

  operator fun invoke(
    login: String,
    passwordHash: String,
    position: Position
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val basketAddDto = remoteService.add(
        login = login,
        passwordHash = passwordHash,
        positions = listOf(position.toPositionDto())
      )

      if (basketAddDto.status != ResponseStatus.ERROR) {
        Data.Success(value = Unit)

        val basketItems = remoteService
          .getContent(
            login = login,
            passwordHash = passwordHash
          )
          .map { it.toBasketItem() }

      } else {
        emit(Data.Error())
      }
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}