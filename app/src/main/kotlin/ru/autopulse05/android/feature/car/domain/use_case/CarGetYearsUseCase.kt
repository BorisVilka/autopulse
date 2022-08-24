package ru.autopulse05.android.feature.car.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.car.data.remote.CarRemoteService
import ru.autopulse05.android.shared.domain.util.Data

class CarGetYearsUseCase(
  private val remoteService: CarRemoteService
) {
  operator fun invoke(
    login: String,
    password: String
  ) : Flow<Data<List<Int>>> = flow {
    try {
      emit(Data.Loading())

      val years = remoteService.getYears(
        login = login,
        password = password
      )

      emit(Data.Success(value = years))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}