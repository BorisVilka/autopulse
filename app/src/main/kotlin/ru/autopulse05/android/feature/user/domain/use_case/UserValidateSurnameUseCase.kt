package ru.autopulse05.android.feature.user.domain.use_case

import ru.autopulse05.android.shared.domain.util.Data

class UserValidateSurnameUseCase {
  operator fun invoke(value: String): Data<Unit> = when {
    value.isBlank() -> Data.Error(
      message = "Введите фамилию"
    )
    else -> Data.Success(value = Unit)
  }
}