package ru.autopulse05.android.feature.vin.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import ru.autopulse05.android.feature.vin.data.remote.VinRemoteService
import ru.autopulse05.android.feature.vin.data.remote.dto.ChatDto

class VinGetChatUseCase(
    private val remoteService: VinRemoteService
) {

    operator fun invoke(
        siteHash: String,
        accessHash: String,
        queryId: String
    ): Flow<List<ChatDto>> = flow {
        try {
            var map = remoteService.getChat(
                siteHash = siteHash,
                accessHash = accessHash
            ).chatList[queryId]
            if(map==null) map = listOf()
            emit(map)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(listOf())
        }
    }
}