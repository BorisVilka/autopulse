package ru.autopulse05.android.feature.cart.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.autopulse05.android.feature.cart.data.remote.dto.CartAddDto
import ru.autopulse05.android.feature.cart.data.remote.dto.CartClearDto
import ru.autopulse05.android.feature.cart.data.remote.dto.CartItemDto
import ru.autopulse05.android.feature.cart.data.remote.dto.PositionDto


interface CartRemoteService {

  @POST(CartHttpRoutes.ADD)
  suspend fun add(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("positions[0][brand]") brand: String,
    @Query("positions[0][number]") number: String,
    @Query("positions[0][itemKey]") itemKey: String,
    @Query("positions[0][supplierCode]") supplierCode: String,
    @Query("positions[0][quantity]") quantity: Int,
    @Query("basketId") basketId: String? = null
  ): CartAddDto

  @POST(CartHttpRoutes.CLEAR)
  suspend fun clear(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("basketId") basketId: String? = null
  ): CartClearDto

  @GET(CartHttpRoutes.CONTENT)
  suspend fun getContent(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("basketId") basketId: String? = null
  ): List<CartItemDto>
}