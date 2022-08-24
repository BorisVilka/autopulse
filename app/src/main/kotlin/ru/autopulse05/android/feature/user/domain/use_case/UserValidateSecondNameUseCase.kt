package ru.autopulse05.android.feature.user.domain.use_case

import ru.autopulse05.android.shared.domain.util.Data

class UserValidateSecondNameUseCase {
  operator fun invoke(value: String): Data<Unit> = when {
    value.isBlank() -> Data.Error(
      message = "Введите отчество"
    )
    else -> Data.Success(value = Unit)
  }
}