package ru.autopulse05.android.feature.car.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.car.data.remote.CarRemoteService
import ru.autopulse05.android.feature.car.data.remote.dto.toCarMark
import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.shared.domain.util.Data

class CarGetMarksUseCase(
  private val remoteService: CarRemoteService
) {

  operator fun invoke(
    login: String,
    password: String
  ): Flow<Data<List<CarMark>>> = flow {
    try {
      emit(Data.Loading())

      val markDtoList = remoteService.getMarks(
        login = login,
        password = password
      )

      emit(Data.Success(value = markDtoList.map { it.toCarMark() }))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}