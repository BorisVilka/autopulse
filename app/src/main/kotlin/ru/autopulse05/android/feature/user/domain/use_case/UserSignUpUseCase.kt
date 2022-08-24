package ru.autopulse05.android.feature.user.domain.use_case


import android.app.Application
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.preferences.presentation.ext.dataStore
import ru.autopulse05.android.feature.user.data.remote.UserRemoteService
import ru.autopulse05.android.feature.user.data.remote.dto.toUser
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.feature.user.domain.util.SignUpMarket
import ru.autopulse05.android.shared.data.ext.toMD5
import ru.autopulse05.android.shared.data.remote.ResponseStatus
import ru.autopulse05.android.shared.domain.util.Data

class UserSignUpUseCase(
  private val application: Application,
  private val remoteService: UserRemoteService,
  private val repository: UserRepository
) {

  operator fun invoke(
    name: String,
    secondName: String,
    surname: String,
    password: String,
    mobile: String,
    office: String,
    email: String,
    icq: String,
    regionId: String,
    city: String,
    organizationName: String,
    organizationForm: String,
    organizationOfficialName: String,
    organizationOfficialAddress: String,
    deliveryAddress: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val addUserDto = remoteService.new(
        marketType = SignUpMarket.Wholesale.type,
        name = name,
        surname = surname,
        secondName = secondName,
        password = password,
        mobile = mobile,
        office = office,
        email = email,
        icq = icq,
        regionId = regionId,
        city = city,
        organizationName = organizationName,
        organizationForm = organizationForm,
        organizationOfficialName = organizationOfficialName,
        organizationOfficialAddress = organizationOfficialAddress,
        deliveryAddress = deliveryAddress
      )

      if (addUserDto.status != ResponseStatus.ERROR) {
        val passwordHash = password.toMD5()
        val user = remoteService
          .getInfo(
            login = mobile,
            passwordHash = passwordHash
          )
          .toUser()

        repository.set(entity = user)
        application.dataStore.updateData {
          it.copy(
            login = mobile,
            passwordHash = passwordHash
          )
        }

        emit(Data.Success(value = Unit))
      } else {
        emit(Data.Error(message = addUserDto.errorMessages.toString()))
      }
    } catch (e: Exception) {
      emit(Data.Error(message = e.message.toString()))
    }
  }

  operator fun invoke(
    name: String,
    secondName: String,
    surname: String,
    mobile: String,
    password: String,
    office: String,
    email: String,
    city: String,
    deliveryAddress: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val addUserDto = remoteService.new(
        marketType = SignUpMarket.Retail.type,
        name = name,
        secondName = secondName,
        surname = surname,
        password = password,
        mobile = mobile,
        office = office,
        email = email,
        city = city,
        deliveryAddress = deliveryAddress
      )

      if (addUserDto.status != ResponseStatus.ERROR) {
        val passwordHash = password.toMD5()
        val user = remoteService
          .getInfo(
            login = mobile,
            passwordHash = passwordHash
          )
          .toUser()

        repository.set(entity = user)
        application.dataStore.updateData {
          it.copy(
            login = mobile,
            passwordHash = passwordHash
          )
        }

        emit(Data.Success(value = Unit))
      } else {
        emit(Data.Error(message = addUserDto.errorMessages.toString()))
      }
    } catch (e: Exception) {
      emit(Data.Error(message = e.message.toString()))
    }
  }
}