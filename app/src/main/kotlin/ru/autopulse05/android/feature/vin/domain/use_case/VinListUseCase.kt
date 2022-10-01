package ru.autopulse05.android.feature.vin.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.vin.data.remote.VinRemoteService
import ru.autopulse05.android.feature.vin.data.remote.dto.VinDto
import ru.autopulse05.android.shared.domain.util.Data

class VinListUseCase(
    private val remoteService: VinRemoteService
) {


    operator fun invoke(
        siteHash: String,
        accessHash: String,
        userId: String
    ): Flow<Data<List<VinDto>>> = flow {
        emit(Data.Loading())
        val list = remoteService.getList(
            siteHash = siteHash,
            accessHash = accessHash
        )
        val tmp = mutableListOf<VinDto>()
        for(i in list.vinquery.indices) {
            if(list.vinquery[i].clientId==null) continue
            //Log.d("TAG",list.vinquery[i].clientId+" "+userId)
            if(list.vinquery[i].clientId.contentEquals(userId)) tmp.add(list.vinquery[i])
        }
        emit(Data.Success(value = tmp))
    }
}