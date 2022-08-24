package ru.autopulse05.android.feature.car.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.car.data.remote.CarRemoteService
import ru.autopulse05.android.feature.car.data.remote.dto.toCarModification
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.shared.domain.util.Data

class CarGetModificationsUseCase(
  private val remoteService: CarRemoteService
) {

  operator fun invoke(
    login: String,
    password: String,
    markId: Int,
    modelId: Int
  ): Flow<Data<List<CarModification>>> = flow {
    try {
      emit(Data.Loading())

      val modificationDtoList = remoteService.getModifications(
        login = login,
        password = password,
        markId = markId,
        modelId = modelId
      )

      emit(Data.Success(value = modificationDtoList.map { it.toCarModification() }))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}