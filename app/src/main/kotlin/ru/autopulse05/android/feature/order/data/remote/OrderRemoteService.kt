package ru.autopulse05.android.feature.order.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.autopulse05.android.feature.cart.data.remote.dto.PositionDto
import ru.autopulse05.android.feature.order.data.remote.dto.*
import ru.autopulse05.android.shared.data.remote.WholeOrderMode

interface OrderRemoteService {

  @GET(OrderHttpRoutes.ORDERS)
  suspend fun get(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("format") format: String? = "p",
    @Query("skip") skip: Int? = null,
    @Query("limit") limit: Int? = null,

  ): OrdersDto

  @POST(OrderHttpRoutes.INSTANT)
  suspend fun instant(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("positions") positions: List<PositionDto>,
    @Query("paymentMethod") paymentMethod: String,
    @Query("shipmentMethod") shipmentMethod: String,
    @Query("shipmentAddress") shipmentAddress: String,
    @Query("office") shipmentOffice: String,
    @Query("shipmentDate") shipmentDate: String,
    @Query("comment") comment: String,
    @Query("basketId") basketId: String? = null,
    @Query("wholeOrderOnly") wholeOrderMode: Int = WholeOrderMode.OFF,
    @Query("clientOrderNumber") clientOrderNumber: String? = null
  ): CreateOrdersDto

  @POST(OrderHttpRoutes.CANCEL)
  suspend fun cancel(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("id") positionId: String
  ): String

  @POST(OrderHttpRoutes.CREATE)
  suspend fun create(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("number") number: String? = null,
    @Query("createTime") createTime: String? = null,
    @Query("positions") positionIds: List<String>,
    @Query("delivery[methodId]") deliveryMethodId: String,
    @Query("delivery[meetData][address]") address: String,
    @Query("delivery[meetData][person]") person: String,
    @Query("delivery[meetData][contact]") contact: String,
    @Query("delivery[meetData][comment]") comment: String
  ): TsOrderDto

  @GET(OrderHttpRoutes.LIST)
  suspend fun getList(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("number") number: String? = null,
    @Query("agreementId") agreementId: String? = null,
    @Query("managerId") managerId: String? = null,
    @Query("deliveryId") deliveryId: String? = null,
    @Query("brand") brand: String? = null,
    @Query("message") message: String? = null,
    @Query("dateStart") dateStart: String? = null,
    @Query("dateEnd") dateEnd: String? = null,
    @Query("updateDateStart") updateDateStart: String? = null,
    @Query("updateDateEnd") updateDateEnd: String? = null,
    @Query("deadlineDateStart") deadlineDateStart: String? = null,
    @Query("deadlineDateEnd") deadlineDateEnd: String? = null,
    @Query("orderIds") orderIds: String? = null,
    @Query("productIds") productIds: String? = null,
    @Query("positionStatuses") positionStatuses: String? = null,
    @Query("skip") skip: String? = null,
    @Query("limit") limit: String? = null
  ): TsOrdersDto

  @POST(OrderHttpRoutes.ORDER)
  suspend fun order(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("paymentMethod") paymentMethod: String?,
    @Query("shipmentMethod") shipmentMethod: String?,
    @Query("shipmentAddress") shipmentAddress: String?,
    @Query("office") shipmentOffice: String?,
    @Query("shipmentDate") shipmentDate: String? = null,
    @Query("comment") comment: String?,
    @Query("basketId") basketId: String? = null,
    @Query("wholeOrderOnly") wholeOrderMode: Int = WholeOrderMode.OFF,
    @Query("positionIds[]") positionIds: Array<Int>? = null,
    @Query("clientOrderNumber") clientOrderNumber: String? = null,
  )

  // Refunds
  @GET(OrderHttpRoutes.COMPLAINTS)
  suspend fun getRefunds(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("opId") opId: String,
    @Query("orderPickingGoodId") orderPickingGoodId: String? = null,
    @Query("orderPickingGoodIds") orderPickingGoodIds: String? = null,
    @Query("pickingIds") pickingIds: String? = null,
    @Query("oldCoPositionIds") oldCoPositionIds: String? = null,
    @Query("oldItemID") oldItemID: String? = null,
    @Query("itemId") itemId: String? = null,
    @Query("locId") locId: String? = null,
    @Query("dateStart") dateStart: String? = null,
    @Query("dateEnd") dateEnd: String? = null,
    @Query("status") status: Int,
    @Query("type") type: Int,
    @Query("skip") skip: Int? = null,
    @Query("limit") limit: Int? = null,
    @Query("output") output: String? = null,
    @Query("fields") fields: String? = null,
  ): RefundDtoList

  @POST(OrderHttpRoutes.CREATE_COMPLAINT)
  suspend fun createComplaint(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("orderPickingId") orderPickingId: String,
    @Query("positions") positions: List<RefundPositionDto>,
  ): CreateComplaintDto

  // Pickings
  @GET(OrderHttpRoutes.PICKINGS)
  suspend fun getPickings(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("opId") opId: String,
    @Query("limit") limit: String? = null,
    @Query("skip") skip: String? = null,
    @Query("output") output: String? = null,
    @Query("productId") productId: String? = null,
    @Query("itemId") itemId: String? = null,
    @Query("ignoreCanceled") ignoreCanceled: String? = null,
    ): PickingDtoList

  @GET(OrderHttpRoutes.PAYMENT)
  suspend fun getPayment(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String
  ): PaymentDtoList


  @GET(OrderHttpRoutes.OFFICE)
  suspend fun getOffices(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String
  ): ShipmentOfficeListDto

  @POST(OrderHttpRoutes.ADDRESS)
  suspend fun getAddress(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("address") address: String
  ): ShipmentAddressDto
}