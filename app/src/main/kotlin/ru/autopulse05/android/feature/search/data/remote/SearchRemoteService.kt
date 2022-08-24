package ru.autopulse05.android.feature.search.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.autopulse05.android.feature.product.data.remote.dto.*
import ru.autopulse05.android.feature.search.data.remote.dto.*


interface SearchRemoteService {

  @GET(SearchHttpRoutes.BRANDS)
  suspend fun getBrands(
    @Query("userlogin") login: String = "",
    @Query("userpsw") passwordHash: String = "",
    @Query("number") number: String,
    @Query("useOnlineStocks") onlineStocks: Int = OnlineStocksMode.ON,
    @Query("locale") locale: String = "ru_RU"
  ): ProductDtoList

  @GET(SearchHttpRoutes.ARTICLES)
  suspend fun getArticles(
    @Query("userlogin") login: String = "",
    @Query("userpsw") passwordHash: String = "",
    @Query("number") number: String,
    @Query("brand") brand: String,
    @Query("useOnlineStocks") onlineStocks: Int = OnlineStocksMode.ON,
    @Query("disableOnlineFiltering") onlineFiltering: Int = OnlineFilteringMode.ON,
    @Query("withOutAnalogs") analogs: Int = 0
//    @Query("profileId") profileId: String
  ): ProductDtoList

  @POST(SearchHttpRoutes.BATCH)
  suspend fun batch(
    @Query("userlogin") login: String = "",
    @Query("userpsw") passwordHash: String = "",
    @Query("search") search: String
    //    @Query("profileId") profileId: String
  ): ProductDtoList

  @GET(SearchHttpRoutes.HISTORY)
  suspend fun getHistory(
    @Query("userlogin") login: String = "",
    @Query("userpsw") passwordHash: String = ""
  ): HistoryItemDtoList

  @GET(SearchHttpRoutes.TIPS)
  suspend fun getTips(
    @Query("userlogin") login: String = "",
    @Query("userpsw") passwordHash: String = "",
    @Query("number") number: String,
    @Query("locale") locale: String = "ru_RU"
  ): SearchTipDtoList
}