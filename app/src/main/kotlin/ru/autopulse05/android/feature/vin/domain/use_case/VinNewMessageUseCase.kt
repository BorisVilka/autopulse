package ru.autopulse05.android.feature.vin.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import ru.autopulse05.android.feature.vin.data.remote.VinRemoteService
import ru.autopulse05.android.feature.vin.data.remote.dto.NewMessageDto
import ru.autopulse05.android.shared.domain.util.Data
import kotlin.math.floor

class VinNewMessageUseCase(
    private val remoteService: VinRemoteService
) {
    operator fun invoke(
        siteHash: String,
        accessHash: String,
        user: String,
        id: String,
        message: String
    ): Flow<Data<NewMessageDto>> = flow {
        emit(Data.Loading())
        val dto = remoteService.newMessage(
            siteHash = siteHash,
            accessHash = accessHash,
            user = user,
            id = id,
            message = message
        )
        if(dto.status==null || dto.error!=null) emit(Data.Error(dto.error))
        else emit(Data.Success(value = dto))
    }
}