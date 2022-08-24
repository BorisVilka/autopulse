package ru.autopulse05.android.feature.user.domain.use_case

import android.app.Application
import ru.autopulse05.android.R
import ru.autopulse05.android.shared.domain.util.Data
import ru.autopulse05.android.shared.presentation.util.PresentationText

class UserValidateRepeatedPasswordUseCase(
  private val application: Application
) {
  operator fun invoke(value: String, password: String): Data<Unit> = when {
    value.isBlank() -> Data.Error(
      message = application.getString(R.string.user_repeated_password_empty_error)
    )
    value != password -> Data.Error(
      message = application.getString(R.string.user_repeated_password_incorrect_error)
    )
    else -> Data.Success(value = Unit)
  }
}