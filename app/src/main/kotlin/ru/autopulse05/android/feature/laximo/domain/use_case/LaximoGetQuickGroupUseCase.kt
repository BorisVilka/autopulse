package ru.autopulse05.android.feature.laximo.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.LaximoQuickGroupDto
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoCategory
import ru.autopulse05.android.feature.laximo.domain.model.LaximoCategory
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetQuickGroupUseCase(
    private val remoteService: LaximoRemoteService
) {

    operator fun invoke(
        login: String,
        password: String,
        catalog: String,
        vehicleId: String,
        ssd: String,
        locale: String,
    ): Flow<Data<List<LaximoQuickGroupDto>>> = flow {
        try {
            emit(Data.Loading())

            val categories = remoteService
                .getQuickGroup(
                    login = login,
                    password = password,
                    catalog = catalog,
                    vehicleId = vehicleId,
                    ssd = ssd,
                    locale = locale,

                )
            emit(Data.Success(value = categories))
        } catch (e: Exception) {
            emit(Data.Error(message = e.message))
        }
    }
}
