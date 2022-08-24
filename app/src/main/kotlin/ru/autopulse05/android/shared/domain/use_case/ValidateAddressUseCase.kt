package ru.autopulse05.android.shared.domain.use_case

import ru.autopulse05.android.shared.domain.util.Data

class ValidateAddressUseCase {
  operator fun invoke(value: String): Data<Unit> = when {
    value.isBlank() -> Data.Error(
      message = "Адрес не может быть пустым"
    )
    else -> Data.Success(value = Unit)
  }
}