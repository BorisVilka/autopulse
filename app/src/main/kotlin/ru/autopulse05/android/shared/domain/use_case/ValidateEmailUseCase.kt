package ru.autopulse05.android.shared.domain.use_case

import android.app.Application
import android.util.Patterns
import ru.autopulse05.android.R
import ru.autopulse05.android.shared.domain.util.Data

class ValidateEmailUseCase(
  private val application: Application
) {
  operator fun invoke(value: String): Data<Unit> = when {
    value.isBlank() -> Data.Error(
      message = application.getString(R.string.user_email_empty_error)
    )
    !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> Data.Error(
      message = application.getString(R.string.user_email_incorrect_error)
    )
    else -> Data.Success(value = Unit)
  }
}