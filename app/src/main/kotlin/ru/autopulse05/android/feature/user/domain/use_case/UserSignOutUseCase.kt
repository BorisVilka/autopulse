package ru.autopulse05.android.feature.user.domain.use_case

import android.app.Application
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.core.data.source.AppDatabase
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.ext.dataStore
import ru.autopulse05.android.shared.domain.util.Data

class UserSignOutUseCase(
  private val application: Application,
  private val database: AppDatabase
) {

  operator fun invoke(): Flow<Data<Unit>> = flow {
    try {
      emit(Data.Loading())

      database.clear()

      application.dataStore.updateData {
        Preferences()
      }

      emit(Data.Success(value = Unit))
    } catch (e: Exception) {
      emit(Data.Error(message = e.message.toString()))
    }
  }
}