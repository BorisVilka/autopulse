package ru.autopulse05.android.feature.vin.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    comment: String = "",
    parts: List<String>,
    stockMode: StockMode,
    guestInfo: GuestInfo
  ): Flow<Data<String>> = flow {
    try {
      emit(Data.Loading())

      val vinAddDto = remoteService
        .add(
          siteHash = siteHash,
          accessHash = accessHash,
          clientId = clientId,
          carInfo = carInfo.toCarInfoDto(),
          comment = comment,
          parts = parts,
          stockEnable = stockMode.value,
          guestInfo = guestInfo.toGuestInfoDto()
        )

      if (vinAddDto.Error != null) {
        emit(Data.Error(message = vinAddDto.Error))
      } else emit(Data.Success(value = vinAddDto.QueryId!!))

    } catch (e: Exception) {
      emit(Data.Error(message = e.message))
    }
  }

  operator fun invoke(
    siteHash: String,
    accessHash: String,
    clientId: String,
    vin: String,
    frameNumber: String,
    mark: CarMark,
    model: CarModel,
    year: Int,
    modification: CarModification,
    comment: String = "",
    parts: List<String>,
    stockMode: StockMode,
    user: User
  ): Flow<Data<String>> = invoke(
    siteHash = siteHash,
    accessHash = accessHash,
    clientId = clientId,
    carInfo = CarInfo(
      brand = mark.name,
      model = model.name,
      year = year,
      modification = modification.name,
      modificationId = modification.id,
      vin = vin,
      frame = frameNumber
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