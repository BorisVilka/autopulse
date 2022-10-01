package ru.autopulse05.android.feature.vin.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.autopulse05.android.feature.car.domain.model.CarMark
import ru.autopulse05.android.feature.car.domain.model.CarModel
import ru.autopulse05.android.feature.car.domain.model.CarModification
import ru.autopulse05.android.feature.user.domain.model.User
import ru.autopulse05.android.feature.vin.data.remote.VinRemoteService
import ru.autopulse05.android.feature.vin.domain.model.*
import ru.autopulse05.android.shared.domain.util.Data

class VinAddUseCase(
  private val remoteService: VinRemoteService
) {

  operator fun invoke(
    siteHash: String,
    accessHash: String,
    clientId: String,
    carInfo: CarInfo,
    comment: String?,
    parts: List<String>,
    stockMode: StockMode,
    guestInfo: GuestInfo
  ): Flow<Data<String>> = flow {
    try {
      emit(Data.Loading())

      var map = LinkedHashMap<String,RequestBody>()
      map.put("siteHash",siteHash.toRequestBody("text/plain".toMediaTypeOrNull()!!))
      map.put("accessHash",accessHash.toRequestBody("text/plain".toMediaTypeOrNull()!!))
      map.put("clientId",clientId.toRequestBody("text/plain".toMediaTypeOrNull()!!))
      map.put("clientComment","Мобильное приложение".toRequestBody("text/plain".toMediaTypeOrNull()!!))
      map.put("carInfo[brand]",carInfo.brand.toRequestBody("text/plain".toMediaTypeOrNull()!!))
      if(carInfo.model!=null) map.put("carInfo[model]",carInfo.model.toRequestBody("text/plain".toMediaTypeOrNull()!!))
      if(carInfo.year!=null) map.put("carInfo[year]",carInfo.year.toString().toRequestBody("text/plain".toMediaTypeOrNull()!!))
      if(carInfo.modification!=null) map.put("carInfo[modification]",carInfo.modification.toString().toRequestBody("text/plain".toMediaTypeOrNull()!!))
      if(carInfo.modificationId!=null) map.put("carInfo[modificationId]",carInfo.modificationId.toString().toRequestBody("text/plain".toMediaTypeOrNull()!!))
      if(carInfo.vin.isNotEmpty()) map.put("carInfo[vin]",carInfo.vin.toRequestBody("text/plain".toMediaTypeOrNull()!!))
      if(carInfo.frame.isNotEmpty()) map.put("carInfo[frame]",carInfo.frame.toRequestBody("text/plain".toMediaTypeOrNull()!!))
      map.put("stockEnable","0".toRequestBody("text/plain".toMediaTypeOrNull()!!))
      for(i in parts.indices) map.put("parts[$i]",parts[i].toRequestBody("text/plain".toMediaTypeOrNull()!!))
      //map.put("guestInfo[name]",guestInfo.name.toRequestBody("text/plain".toMediaTypeOrNull()!!))
      //map.put("guestInfo[phone]",guestInfo.phone.toRequestBody("text/plain".toMediaTypeOrNull()!!))
      //map.put("guestInfo[email]",guestInfo.email.toRequestBody("text/plain".toMediaTypeOrNull()!!))
      /*val vinAddDto = remoteService
        .add(
          siteHash = siteHash,
          accessHash = accessHash,
          clientId = clientId,
          comment = comment,
          parts = parts.toTypedArray(),
          stockEnable = stockMode.value,
          carVin = if(carInfo.vin.isEmpty()) null else carInfo.vin,
          carFrame = if(carInfo.frame.isEmpty()) null else carInfo.frame,
          carBrand = carInfo.brand,
        )*/
      val vinAddDto = remoteService.add(map)
      if (vinAddDto.error != null) {
        emit(Data.Error(message = vinAddDto.error))
      } else emit(Data.Success(value = vinAddDto.queryId!!))

    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }

  operator fun invoke(
    siteHash: String,
    accessHash: String,
    clientId: String,
    vin: String?,
    frameNumber: String?,
    mark: CarMark?,
    model: CarModel?,
    year: Int?,
    modification: CarModification?,
    comment: String = "",
    parts: List<String>,
    stockMode: StockMode,
    user: User
  ): Flow<Data<String>> = invoke(
    siteHash = siteHash,
    accessHash = accessHash,
    clientId = clientId,
    carInfo = CarInfo(
      brand = mark?.name.orEmpty(),
      model = model?.name,
      year = year,
      modification = modification?.name,
      modificationId = modification?.id,
      vin = vin.orEmpty(),
      frame = frameNumber.orEmpty()
    ),
    comment = comment,
    parts = parts,
    stockMode = stockMode,
    guestInfo = GuestInfo(
      name = user.name,
      phone = user.mobile,
      email = user.email
    )
  )
}