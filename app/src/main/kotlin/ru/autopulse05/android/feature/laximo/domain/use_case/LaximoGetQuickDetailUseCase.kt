package ru.autopulse05.android.feature.laximo.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.LaximoCategoryDto
import ru.autopulse05.android.feature.laximo.data.remote.dto.LaximoUnitDto
import ru.autopulse05.android.feature.laximo.domain.model.LaximoCategory
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetQuickDetailUseCase(
    private val remoteService: LaximoRemoteService
) {

    operator fun invoke(
        login: String,
        password: String,
        catalog: String,
        vehicleId: String,
        ssd: String,
        locale: String,
        quickGroupId: String
    ): Flow<Data<List<LaximoCategoryDto>>> = flow {
        try {
            emit(Data.Loading())

            val categories = remoteService
                .getQuickDetail(
                    login = login,
                    password = password,
                    catalog = catalog,
                    vehicleId = vehicleId,
                    ssd = ssd,
                    locale = locale,
                    quickGroupId = quickGroupId
                    )
            Log.d("TAG","SIZE!: "+categories.size)
            emit(Data.Success(value = categories))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Data.Error(message = e.message))
        }
    }
}