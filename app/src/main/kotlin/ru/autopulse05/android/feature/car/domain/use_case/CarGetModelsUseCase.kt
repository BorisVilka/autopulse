package ru.autopulse05.android.feature.car.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.car.data.remote.CarRemoteService
import ru.autopulse05.android.feature.car.data.remote.dto.toCarModel
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.shared.domain.util.Data

class CarGetModelsUseCase(
  private val remoteService: CarRemoteService
) {

  operator fun invoke(
    login: String,
    password: String,
    markId: Int
  ): Flow<Data<List<CarModel>>> = flow {
    try {
      emit(Data.Loading())

      val modelDtoList = remoteService.getModels(
        login = login,
        password = password,
        markId = markId
      )

      emit(Data.Success(value = modelDtoList.map { it.toCarModel() }))
    } catch (e: Exception) {
      e.printStackTrace()
      emit(Data.Error(message = e.message))
    }
  }
}