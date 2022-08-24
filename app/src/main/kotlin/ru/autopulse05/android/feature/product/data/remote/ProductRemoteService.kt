package ru.autopulse05.android.feature.product.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.autopulse05.android.feature.product.data.remote.dto.*


interface ProductRemoteService {

  // Advices
  @GET(ProductHttpRoutes.ADVICES)
  suspend fun getAdvices(
    @Query("userlogin") login: String = "",
    @Query("userpsw") passwordHash: String = "",
    @Query("brand") brand: String,
    @Query("number") number: String,
    @Query("limit") limit: Int? = null
  ): AdviceDtoList

  @POST(ProductHttpRoutes.ADVICES_BATCH)
  suspend fun advicesBatch(
    @Query("userlogin") login: String = "",
    @Query("userpsw") passwordHash: String = "",
    @Query("articles") articles: List<ArticleDto>,
    @Query("limit") limit: Int? = null
  ): AdvicesDto

  // Brands
  @GET(ProductHttpRoutes.ARTICLES_BRANDS)
  suspend fun getBrands(
    @Query("userlogin") login: String = "",
    @Query("userpsw") passwordHash: String = ""
  ): BrandDtoList

  // Info
  @GET(ProductHttpRoutes.ARTICLES_INFO)
  suspend fun getInfo(
    @Query("userlogin") login: String = "",
    @Query("userpsw") passwordHash: String = "",
    @Query("brand") brand: String,
    @Query("number") number: String,
    @Query("format") format: String = "bnpic",
    @Query("cross_image") crossImage: Int = CrossImageMode.OFF,
    @Query("source") source: String? = null,
    @Query("with_original") withOriginal: String? = null,
    @Query("locale") locale: String = "ru_RU"
  ): ProductInfoDto
}