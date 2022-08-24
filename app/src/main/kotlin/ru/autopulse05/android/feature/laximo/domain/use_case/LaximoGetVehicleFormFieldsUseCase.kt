package ru.autopulse05.android.feature.laximo.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoVehicleFormField
import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicleFormField
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetVehicleFormFieldsUseCase(
  private val remoteService: LaximoRemoteService
) {

  operator fun invoke(
    login: String,
    password: String,
    catalog: String,
    ssd: String,
    locale: String,
  ): Flow<Data<List<LaximoVehicleFormField>>> = flow {
    try {
      emit(Data.Loading())

      val catalogs = remoteService
        .getVehicleFormFields(
          login = login,
          password = password,
          catalog = catalog,
          ssd = ssd,
          locale = locale
        )
        .map { it.toLaximoVehicleFormField() }

      emit(Data.Success(value = catalogs))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }
}
