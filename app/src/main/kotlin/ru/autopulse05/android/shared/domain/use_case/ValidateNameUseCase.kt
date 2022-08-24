package ru.autopulse05.android.shared.domain.use_case

import android.app.Application
import ru.autopulse05.android.R
import ru.autopulse05.android.shared.domain.util.Data

class ValidateNameUseCase(
  private val application: Application
) {
  operator fun invoke(value: String): Data<Unit> = when {
    value.isBlank() -> Data.Error(
      message = application.getString(R.string.user_name_empty_error)
    )
    else -> Data.Success(value = Unit)
  }
}