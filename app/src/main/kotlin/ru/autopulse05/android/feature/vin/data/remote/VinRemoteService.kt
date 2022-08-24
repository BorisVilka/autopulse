package ru.autopulse05.android.feature.vin.data.remote

import retrofit2.http.POST
import retrofit2.http.Query
import ru.autopulse05.android.feature.vin.data.remote.dto.CarInfoDto
import ru.autopulse05.android.feature.vin.data.remote.dto.GuestInfoDto
import ru.autopulse05.android.feature.vin.data.remote.dto.VinAddDto

interface VinRemoteService {
  @POST(VinHttpRoutes.ADD)
  suspend fun add(
    @Query("siteHash") siteHash: String,
    @Query("accessHash") accessHash: String,
    @Query("clientId") clientId: String,
    @Query("carInfo") carInfo: CarInfoDto,
    @Query("clientComment") comment: String,
    @Query("parts") parts: List<String>,
    @Query("stockEnable") stockEnable: Int,
    @Query("guestInfo") guestInfo: GuestInfoDto
  ): VinAddDto
}