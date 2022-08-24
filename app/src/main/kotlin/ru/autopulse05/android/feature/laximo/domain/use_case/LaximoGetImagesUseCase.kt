package ru.autopulse05.android.feature.laximo.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.LaximoImageDto
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoUnit
import ru.autopulse05.android.feature.laximo.domain.model.LaximoUnit
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetImagesUseCase(
    private val remoteService: LaximoRemoteService
) {

    operator fun invoke(
        login: String,
        password: String,
        catalog: String,
        unitId: String,
        ssd: String,
    ): Flow<Data<List<LaximoImageDto>>> = flow {
        try {
            emit(Data.Loading())

            val unit = remoteService
                .getImageCodes(
                    login = login,
                    password = password,
                    catalog = catalog,
                    unitId = unitId,
                    ssd =  ssd
                )
            var ans = mutableListOf<LaximoImageDto>()
            for(i in unit.indices) {
                var tmp = true
                for(j in ans.indices) {
                    if(ans[j].code==unit[i].code) {
                        tmp = false
                        break
                    }
                }
                if(tmp) ans.add(unit[i])
            }
            for(i in ans.indices) Log.d("TAG","IMAGES ${ans[i].code}")
            emit(Data.Success(value = ans))
        } catch (e: Exception) {
            emit(Data.Error(message = e.message))
        }
    }
}