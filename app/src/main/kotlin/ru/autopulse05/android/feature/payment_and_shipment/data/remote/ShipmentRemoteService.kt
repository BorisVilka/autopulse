package ru.autopulse05.android.feature.payment_and_shipment.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.autopulse05.android.feature.payment_and_shipment.data.remote.dto.*


interface ShipmentRemoteService {

  @GET(ShipmentHttpRoutes.METHODS)
  suspend fun getMethods(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String
  ): ShipmentMethodDtoList

  @GET(ShipmentHttpRoutes.OFFICES)
  suspend fun getOffices(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("officesType") officesType: String? = null
  ): ShipmentOfficeDtoList

  @GET(ShipmentHttpRoutes.ADDRESSES)
  suspend fun getAddresses(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String
  ): ShipmentAddressDtoList

  @GET(ShipmentHttpRoutes.DATES)
  suspend fun getDates(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("minDeadlineTime") minDeadlineTime: String,
    @Query("maxDeadlineTime") maxDeadlineTime: String,
    @Query("addShipmentAddress") shipmentAddress: String? = null
  ): ShipmentDateDtoList

  @POST(ShipmentHttpRoutes.ADDRESS)
  suspend fun addAddress(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("address") address: String
  ): ShipmentAddressAddDto
}