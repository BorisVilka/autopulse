package ru.autopulse05.android.feature.laximo.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.LaximoApplicationDto
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoCategory
import ru.autopulse05.android.feature.laximo.domain.model.LaximoCategory
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetApplicationUseCase(
    private val remoteService: LaximoRemoteService
) {

    operator fun invoke(
        login: String,
        password: String,
        catalog: String,
        ssd: String,
        oem: String,
        locale: String
    ): Flow<Data<List<LaximoApplicationDto>>> = flow {
        try {
            emit(Data.Loading())
            Log.d("TAG","FFF $catalog $oem")
            val categories = remoteService
                .getApplication(
                    login = login,
                    password = password,
                    ssd = ssd,
                    catalog = catalog,
                    oem = oem,

                )
            emit(Data.Success(value = categories))
        } catch (e: Exception) {
            emit(Data.Error(message = e.message))
        }
    }
}
