package ru.autopulse05.android.feature.user.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.user.data.remote.UserRemoteService
import ru.autopulse05.android.feature.user.data.remote.dto.toUser
import ru.autopulse05.android.feature.user.domain.model.User
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.shared.domain.util.Data

class UserUpdateUseCase(
  private val repository: UserRepository,
  private val remoteService: UserRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String
  ): Flow<Data<User>> = flow {
    try {
      emit(Data.Loading())

      val user = remoteService
        .getInfo(
          login = login,
          passwordHash = passwordHash
        )
        .toUser()

      repository.set(entity = user)

      emit(Data.Success(value = user))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message.toString()))
    }
  }
}