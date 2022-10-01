package ru.autopulse05.android.feature.vin.data.remote

import okhttp3.RequestBody
import retrofit2.http.*
import ru.autopulse05.android.feature.vin.data.remote.dto.*

interface VinRemoteService {

  @POST(VinHttpRoutes.ADD)
  suspend fun add(
    @Query("siteHash") siteHash: String,
    @Query("accessHash") accessHash: String,
    @Query("clientId") clientId: String,
    @Query("carInfo[brand]") carBrand: String,
    @Query("carInfo[vin]") carVin: String?,
    @Query("carInfo[frame]") carFrame: String?,
    @Query("clientComment") comment: String?,
    @Query("parts[]") parts: Array<String>,
    @Query("stockEnable") stockEnable: Int,
  ): VinAddDto

  @Multipart
  @JvmSuppressWildcards
  @POST(VinHttpRoutes.ADD)
  suspend fun add(
    @PartMap map: Map<String,RequestBody>
  ):VinAddDto


  @GET(VinHttpRoutes.LIST)
  suspend fun getList(
    @Query("siteHash") siteHash: String,
    @Query("accessHash") accessHash: String,
    @Query("limit") limit: Int = 100
    ): VinListDto


  @GET(VinHttpRoutes.CHAT)
  suspend fun getChat(
    @Query("siteHash") siteHash: String,
    @Query("accessHash") accessHash: String
  ): ChatListDto


  @POST(VinHttpRoutes.MESSAGE)
  suspend fun newMessage(
    @Query("siteHash") siteHash: String,
    @Query("accessHash") accessHash: String,
    @Query("userId") user: String,
    @Query("id") id: String,
    @Query("comment") message: String
  ): NewMessageDto

  @POST(VinHttpRoutes.RETURN)
  suspend fun onUpdate(
    @Query("siteHash") siteHash: String,
    @Query("accessHash") accessHash: String,
    @Query("id") id: String,
  ): NewMessageDto
}