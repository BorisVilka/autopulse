package ru.autopulse05.android.feature.user.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.user.data.remote.UserRemoteService
import ru.autopulse05.android.shared.domain.util.Data

class UserRestorePasswordUseCase(
  private val remoteService: UserRemoteService
) {

  operator fun invoke(
    phone: String,
    newPassword: String,
    code: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val restoreDto = remoteService
        .restore(
          emailOrMobile = phone,
          newPassword = newPassword,
          code = code
        )

      if (restoreDto.errorCode != null)
        emit(Data.Error(message = restoreDto.errorMessage))
      else emit(Data.Success(value = Unit))

    } catch (e: Exception) {
      emit(Data.Error(message = e.message.toString()))
    }
  }
}