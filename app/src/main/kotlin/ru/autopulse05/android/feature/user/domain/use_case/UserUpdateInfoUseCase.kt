package ru.autopulse05.android.feature.user.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.user.data.remote.UserRemoteService
import ru.autopulse05.android.feature.user.data.remote.dto.toUser
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.shared.domain.util.Data

class UserUpdateInfoUseCase(
  private val repository: UserRepository,
  private val remoteService: UserRemoteService
) {

  suspend operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      val user = remoteService
        .getInfo(
          login = login,
          passwordHash = passwordHash
        )
        .toUser()

      repository.update(entity = user)

      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message.toString()))
    }
  }
}