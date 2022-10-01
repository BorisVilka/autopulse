package ru.autopulse05.android.feature.user.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.autopulse05.android.feature.user.data.remote.dto.AddUserDto
import ru.autopulse05.android.feature.user.data.remote.dto.PaymentDto
import ru.autopulse05.android.feature.user.data.remote.dto.UserDto
import ru.autopulse05.android.feature.user.data.remote.dto.UserRestoreDto

interface UserRemoteService {

  @POST(UserHttpRoutes.NEW)
  suspend fun new(
    @Query("market") marketType: String,
    @Query("filialId") filialId: String = "",
    @Query("name") name: String,
    @Query("secondName") secondName: String,
    @Query("surname") surname: String,
    @Query("password") password: String,
    @Query("birthDate") birthDate: String = "",
    @Query("mobile") mobile: String = "",
    @Query("memberOfClub") memberOfClub: String = "",
    @Query("office") office: String,
    @Query("email") email: String,
    @Query("icq") icq: String = "",
    @Query("skype") skype: String = "",
    @Query("regionId") regionId: String = "",
    @Query("city") city: String,
    @Query("entityName") organizationName: String = "",
    @Query("business") business: String = "",
    @Query("organizationForm") organizationForm: String = "",
    @Query("organizationOfficialName") organizationOfficialName: String = "",
    @Query("inn") inn: String = "",
    @Query("kpp") kpp: String = "",
    @Query("ogrn") ogrn: String = "",
    @Query("organizationOfficialAddress") organizationOfficialAddress: String = "",
    @Query("bankName") bankName: String = "",
    @Query("bik") bik: String = "",
    @Query("correspondentAccount") correspondentAccount: String = "",
    @Query("organizationAccount") organizationAccount: String = "",
    @Query("deliveryAddress") deliveryAddress: String,
    @Query("comment") comment: String = "",
    @Query("sendRegistrationEmail") registrationEmailMode: Int = RegistrationEmailMode.SEND
  ): AddUserDto

  @GET(UserHttpRoutes.INFO)
  suspend fun getInfo(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String
  ): UserDto

  @POST(UserHttpRoutes.RESTORE)
  suspend fun restore(
    @Query("phone") emailOrMobile: String? = null,
    @Query("passwordNew") newPassword: String? = null,
    @Query("code") code: String? = null
  ): UserRestoreDto

  @GET(UserHttpRoutes.Payments)
  suspend fun getPayments(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("userId") id: String,
    @Query("createDateTimeStart") start: String,
    @Query("createDateTimeEnd") end: String
  ): List<PaymentDto>
}