package ru.autopulse05.android.feature.user.domain.use_case

import android.app.Application
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.preferences.presentation.ext.dataStore
import ru.autopulse05.android.feature.user.data.remote.UserRemoteService
import ru.autopulse05.android.feature.user.data.remote.dto.toUser
import ru.autopulse05.android.feature.user.domain.model.User
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.shared.data.ext.toMD5
import ru.autopulse05.android.shared.domain.util.Data

class UserSignInUseCase(
  private val application: Application,
  private val repository: UserRepository,
  private val remoteService: UserRemoteService
) {

  operator fun invoke(
    login: String,
    password: String
  ): Flow<Data<User>> = flow {
    try {
      emit(Data.Loading())

      val passwordHash = password.toMD5()
      val user = remoteService
        .getInfo(
          login = login,
          passwordHash = passwordHash
        )
        .toUser()

      repository.set(entity = user)
      application.dataStore.updateData {
        it.copy(
          login = login,
          passwordHash = passwordHash
        )
      }

      emit(Data.Success(value = user))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message.toString()))
    }
  }
}