package ru.autopulse05.android.feature.basket.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.autopulse05.android.feature.basket.data.remote.dto.*


interface BasketRemoteService {

  @POST(BasketHttpRoutes.ADD)
  suspend fun add(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("positions") positions: List<PositionDto>,
    @Query("basketId") basketId: String? = null
  ): BasketAddDto

  @POST(BasketHttpRoutes.CLEAR)
  suspend fun clear(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("basketId") basketId: String? = null
  ): BasketClearDto

  @GET(BasketHttpRoutes.CONTENT)
  suspend fun getContent(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("basketId") basketId: String? = null
  ): List<BasketItemDto>

  @GET(BasketHttpRoutes.OPTIONS)
  suspend fun getOptions(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String
  ): BasketOptionsItemDtoList
}