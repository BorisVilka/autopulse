package ru.autopulse05.android.feature.user.domain.use_case

import android.app.Application
import ru.autopulse05.android.R
import ru.autopulse05.android.shared.domain.util.Data

class UserValidatePasswordUseCase(
  private val application: Application
) {
  operator fun invoke(value: String): Data<Unit> = when {
    value.isBlank() -> Data.Error(
      message = application.getString(R.string.user_password_empty_error)
    )
    value.length < 4 -> Data.Error(
      message = application.getString(R.string.user_password_incorrect_error)
    )
    else -> Data.Success(value = Unit)
  }
}