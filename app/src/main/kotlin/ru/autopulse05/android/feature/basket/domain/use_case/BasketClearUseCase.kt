package ru.autopulse05.android.feature.basket.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.basket.data.remote.BasketRemoteService
import ru.autopulse05.android.feature.basket.data.remote.dto.PositionDto
import ru.autopulse05.android.feature.basket.data.remote.dto.toBasketItem
import ru.autopulse05.android.feature.basket.domain.model.BasketItem
import ru.autopulse05.android.feature.basket.domain.repository.BasketRepository
import ru.autopulse05.android.shared.data.remote.ResponseStatus
import ru.autopulse05.android.shared.domain.util.Data

class BasketClearUseCase(
 private val remoteService: BasketRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val basketClearDto = remoteService.clear(
        login = login,
        passwordHash = passwordHash
      )

      if (basketClearDto.status != ResponseStatus.ERROR) {
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
    items: List<BasketItem>
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val basketAddDto = remoteService.add(
        login = login,
        passwordHash = passwordHash,
        positions = items.map {
          PositionDto(
            brand = it.brand,
            number = it.number,
            numberFix = it.numberFix,
            code = it.code,
            supplierCode = it.supplierCode,
            description = it.description,
            quantity = 0,
            comment = "",
          )
        }
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
}