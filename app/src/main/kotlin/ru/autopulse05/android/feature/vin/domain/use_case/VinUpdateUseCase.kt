package ru.autopulse05.android.feature.vin.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import ru.autopulse05.android.feature.vin.data.remote.VinRemoteService
import ru.autopulse05.android.feature.vin.data.remote.dto.NewMessageDto

class VinUpdateUseCase(
    private val remoteService: VinRemoteService
) {

    operator fun invoke(
       siteHash: String,
       accessHash: String,
       id: String,
    ): Flow<NewMessageDto> = flow {

        val dto = remoteService.onUpdate(
            siteHash = siteHash,
            accessHash = accessHash,
            id = id
        )
        emit(dto)
    }
}